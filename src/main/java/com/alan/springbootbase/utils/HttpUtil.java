package com.alan.springbootbase.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
/**
 * @description: HTTP工具类
 * @author: Alan
 * @create: 2019-08-06 17:58
 **/
public class HttpUtil {
    /** 请求超时*/
    private final static int REQ_TIME_OUT = 10000;

    /** 连接超时*/
    private final static int CON_TIME_OUT = 10000;

    /** 设置请求和传输超时时间*/
    private static final RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(REQ_TIME_OUT).setConnectTimeout(CON_TIME_OUT).build();

    /** 默认编码字符集*/
    private static final String DEFAULT_CHARSET = "utf-8";

    /** 重试次数 默认为三次*/
    private final static int DEFAULT_REPEAT_TIMES = 3;

    private static int repeatTime = DEFAULT_REPEAT_TIMES;

    private final static String DEFAULT_STR_MARK = "Dds12312weqweq1412qwe1";

    /**
     * @Description:
     */
    private HttpUtil() {
        // TODO Auto-generated constructor stub
    }
    /**
     * Get请求 默认失败重试 (默认三次 不包含第一次)
     * @param httpUrl
     * @return
     * @throws IOException
     */
    public static String doGet(String httpUrl) throws IOException {
        return doGetUrlEncoding(httpUrl, null, true);
    }
    /**
     *请求返回cookie
     * @param httpUrl 请求路径
     * @param parameters 拼装请求参数
     * @param isRepeat 失败是否重试 (默认三次 不包含第一次)
     * @return
     * @throws IOException
     */
    public static String doGetReturnHeaderCookies(String httpUrl,Map<String,String> parameters,boolean isRepeat) throws IOException {
        return doGetHttpRequest(httpUrl, parameters, isRepeat,true);
    }
    /**
     *get 请求并对参数加密
     * @param httpUrl 请求路径
     * @param parameters
     * @param isRepeat 失败是否重试 (默认三次 不包含第一次)
     * @return
     * @throws IOException
     */
    public static String doGetUrlEncoding(String httpUrl,Map<String,String> parameters,boolean isRepeat) throws IOException{
        return doGetHttpRequest(httpUrl, parameters, isRepeat, false);
    }
    /**
     *get 请求
     * @param httpUrl 请求路径
     * @param parameters 参数
     * @param isRepeat  失败是否重试 (默认三次 不包含第一次)
     * @param headers 请求头信息
     * @return
     * @throws IOException
     */
    public static String doGet(String httpUrl,Map<String,String> parameters,boolean isRepeat,Header ...headers) throws IOException {
        return doGetHttpRequest(httpUrl, parameters, isRepeat, false,headers);
    }

    /**
     * Send a post request
     * @param url         Url as string
     * @param body        Request body as string
     * @return response   Response as string
     * @throws IOException
     */
    static public String postJson(String url, String body) throws IOException {
        return postJson(url, body, null);
    }

    /**
     * Send a post request
     * @param url         Url as string
     * @param body        Request body as string
     * @param headers     Optional map with headers
     * @return response   Response as string
     * @throws IOException
     */
    static public String postJson(String url, String body,
                              Map<String, String> headers) throws IOException {
        headers.put("Content-Type", "application/json");
        return fetch("POST", url, body, headers);
    }
    /**
     * Send a request
     * @param method      HTTP method, for example "GET" or "POST"
     * @param url         Url as string
     * @param body        Request body as string
     * @param headers     Optional map with headers
     * @return response   Response as string
     * @throws IOException
     */
    static public String fetch(String method, String url, String body,
                               Map<String, String> headers) throws IOException {
        // connection
        URL u = new URL(url);
        HttpURLConnection conn = (HttpURLConnection)u.openConnection();
        conn.setConnectTimeout(60000);
        conn.setReadTimeout(60000);

        // method
        if (method != null) {
            conn.setRequestMethod(method);
        }
        // headers
        if (headers != null) {
            for(String key : headers.keySet()) {
                conn.addRequestProperty(key, headers.get(key));
            }
        }
        // body
        if (body != null) {
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            os.write(body.getBytes("UTF-8"));
            os.flush();
            os.close();
        }

        // response
        InputStream is = conn.getInputStream();
        String response = streamToString(is);
        is.close();
        // handle redirects
        if (conn.getResponseCode() == 301) {
            String location = conn.getHeaderField("Location");
            return fetch(method, location, body, headers);
        }
        return response;
    }

    /**
     * Read an input stream into a string
     * @param in
     * @return
     * @throws IOException
     */
    static public String streamToString(InputStream in) throws IOException {
//		StringBuffer out = new StringBuffer();
//		byte[] b = new byte[4096];
//		for (int n; (n = in.read(b)) != -1;) {
//			out.append(new String(b, 0, n));
//		}
//		return out.toString();
        StringBuffer out = new StringBuffer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in,"utf-8"));
        String line = "";
        while((line = reader.readLine()) != null){
            out.append(line);
            //System.out.println(line);
        }
        return out.toString();
    }

    /**
     *Post请求
     * @param httpUrl 请求路径
     * @param parameters 请求参数
     * @param isRepeat 是否重试 如果true: 则自动重试三次 不包含第一次
     * @return
     * @throws IOException
     */
    public static String doPost(String httpUrl,Map<String,String> parameters,boolean isRepeat) throws IOException {
        return doPost(null,httpUrl, parameters, isRepeat,new Header[]{});
    }
    /**
     * POST 提交
     * contentType 默认为 application/json
     * @param httpUrl 请求路径
     * @param requestBody 请求BODY
     * @param isRepeat 是否重试 默认重复3次 不包含第一次
     * @param header 头信息
     * @return
     * @throws IOException
     */
    public static String doPost(String httpUrl,String requestBody,Boolean isRepeat,Header ...header) throws IOException {
        String returnDatas = doPostByRequestBody(null, httpUrl, requestBody, isRepeat,ContentType.APPLICATION_JSON, header);
        if(StringUtils.isBlank(returnDatas)) {
            if(StringUtils.isBlank(returnDatas)) {
                if(isRepeat) {
                    AtomicInteger integer = new AtomicInteger(0);
                    while(repeatTime != integer.get()) {
                        System.out.println("正在重试第["+(integer.incrementAndGet())+"]次~");
                        returnDatas = doPostByRequestBody(null, httpUrl, requestBody, isRepeat,ContentType.APPLICATION_JSON, header);
                        if(StringUtils.equals(returnDatas, DEFAULT_STR_MARK) || StringUtils.isNotBlank(returnDatas)) {
                            break;
                        }
                    }
                }
            }
        }
        return StringUtils.equals(returnDatas, DEFAULT_STR_MARK) ? null : returnDatas;
    }

    /**
     * HTTP RequestBody 参数
     * POST 提交
     * @Title: doPost
     * @param httpUrl 请求路径
     * @param requestBody 请求BODY
     * @param isRepeat 是否重试 默认重复3次 不包含第一次
     * @param header 头信息
     * @return
     * @author: Omar(OmarZhang)
     * @throws IOException
     * @throws IOException
     * @date: 2016年4月18日 下午6:12:36
     */
    public static String doPost(String httpUrl,String requestBody,Boolean isRepeat,ContentType contentType,Header[] header) throws IOException {
        String returnDatas = doPostByRequestBody(null, httpUrl, requestBody, isRepeat,contentType, header);
        if(StringUtils.isBlank(returnDatas)) {
            if(StringUtils.isBlank(returnDatas)) {
                if(isRepeat) {
                    AtomicInteger integer = new AtomicInteger(0);
                    while(repeatTime != integer.get()) {
                        System.out.println("正在重试第["+(integer.incrementAndGet())+"]次~");
                        returnDatas = doPostByRequestBody(null, httpUrl, requestBody, isRepeat,contentType, header);
                        if(StringUtils.equals(returnDatas, DEFAULT_STR_MARK) || StringUtils.isNotBlank(returnDatas)) {
                            break;
                        }
                    }
                }
            }
        }
        return StringUtils.equals(returnDatas, DEFAULT_STR_MARK) ? null : returnDatas;
    }

    /**
     * 通过RequestBody
     * @Title: doPostByRequestBody
     * @param httpClient
     * @param httpUrl
     * @param requestBody
     * @param isRepeat
     * @param header
     * @param contentType
     * @return
     * @throws IOException
     * @author: Omar(OmarZhang)
     * @date: 2016年4月18日 下午6:23:01
     */
    private static String doPostByRequestBody(CloseableHttpClient httpClient,String httpUrl,String requestBody,Boolean isRepeat,ContentType contentType, Header ...header) throws IOException  {
        if(httpClient == null) {
            httpClient =  HttpClients.custom().build();
        }
        HttpPost httpPost = new HttpPost(httpUrl);
        httpPost.setConfig(requestConfig);
        if(header != null && header.length > 0) {
            httpPost.setHeaders(header);
        }
        httpPost.setEntity(new StringEntity(requestBody,contentType));
        CloseableHttpResponse response = null;
        String returnData = null;
        HttpEntity respEntity = null;
        try {
            response = httpClient.execute(httpPost);
            respEntity = response.getEntity();
            if(respEntity != null) {
                returnData  = EntityUtils.toString(respEntity, DEFAULT_CHARSET);
                System.out.println("HTTP 请求 Response == >" +returnData);
                if(response.getStatusLine().getStatusCode() >= 200 && response.getStatusLine().getStatusCode() < 300) {
                    //判断 如果请求成功 就算没有数据 也算成功 则表示不需要重试.标志加入
                    if(StringUtils.isBlank(returnData)) {
                        returnData = DEFAULT_STR_MARK;
                    }
                }else {
                    returnData = null;
                }
            }
            EntityUtils.consume(respEntity);
        } finally {
            HttpClientUtils.closeQuietly(response);
            HttpClientUtils.closeQuietly(httpClient);
        }
        return returnData;
    }
    /**
     * Post请求默认重试
     * 默认重试,重试次数3次不包括第一次
     * @Title: doPost
     * @param httpUrl 请求路径
     * @param parameters 请求参数
     * @param header  头信息设置
     * @return
     * @throws IOException
     */
    public static String doPost(String httpUrl,Map<String, String> parameters,Header ...header) throws IOException {
        return doPost(null,httpUrl, parameters, true,header);
    }
    /**
     * Post请求默认重试
     * 默认不重试
     * @Title: doPost
     * @param httpClient 自定义 http客户端
     * @param httpUrl 请求路径
     * @param parameters 请求参数
     * @param header 头信息设置
     * @return
     * @throws IOException
     */
    public static String doPost(CloseableHttpClient httpClient,String httpUrl,Map<String, String> parameters,Header ...header) throws IOException {
        return doPost(httpClient,httpUrl, parameters, false,header);
    }
    /**
     * Post请求
     * @Title: doPost
     * @param httpUrl 请求路径
     * @param parameters 请求参数
     * @param isRepeat 是否重试 如果true: 则自动重试三次 不包含第一次
     * @param header 头信息设置
     * @return
     * @throws IOException
     */
    private static String doPost(CloseableHttpClient httpClient,String httpUrl,Map<String, String> parameters,boolean isRepeat, Header ...header) throws IOException {
        String returnDatas = null;
        returnDatas = doPostHttpRequet(httpClient,httpUrl,parameters,header);
        if(StringUtils.isBlank(returnDatas)) {
            if(isRepeat) {
                AtomicInteger integer = new AtomicInteger(0);
                while(repeatTime != integer.get()) {
                    System.out.println("正在重试第["+(integer.incrementAndGet())+"]次~");
                    returnDatas = doPostHttpRequet(httpClient,httpUrl,parameters,header);
                    if(StringUtils.equals(returnDatas, DEFAULT_STR_MARK) || StringUtils.isNotBlank(returnDatas)) {
                        break;
                    }
                }
            }
        }
        return StringUtils.equals(returnDatas, DEFAULT_STR_MARK) ? null : returnDatas;
    }
    /**
     * Post 请求
     * @param httpclient
     * @param httpUrl
     * @param parameters
     * @param header
     * @return
     * @throws IOException
     */
    private static String doPostHttpRequet(CloseableHttpClient httpclient,String httpUrl, Map<String, String> parameters, Header ...header) throws IOException {
        if(httpclient == null) {
            httpclient = HttpClients.custom().build();
        }
        HttpPost httpPost = new HttpPost(httpUrl);
        httpPost.setConfig(requestConfig);
        List<NameValuePair> valuePairs = new LinkedList<NameValuePair>();
        String returnData = null;
        if(MapUtils.isNotEmpty(parameters)) {
            for(Map.Entry<String, String> entry : parameters.entrySet()) {
                valuePairs.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
            }
        }
        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(valuePairs, DEFAULT_CHARSET);
        if(header != null && header.length > 0) {
            httpPost.setHeaders(header);
        }
        httpPost.setEntity(urlEncodedFormEntity);
        CloseableHttpResponse response = httpclient.execute(httpPost);
        try {
            HttpEntity respEntity = response.getEntity();
            if(respEntity != null) {
                returnData = EntityUtils.toString(respEntity, DEFAULT_CHARSET);
                System.out.println("HTTP 请求 Response == >" +returnData);
                if(response.getStatusLine().getStatusCode() >= 200 && response.getStatusLine().getStatusCode() < 300) {
                    //判断 如果请求成功 就算没有数据 也算成功 则表示不需要重试.标志加入
                    if(StringUtils.isBlank(returnData)) {
                        returnData = DEFAULT_STR_MARK;
                    }
                }else {
                    returnData = null;
                }
            }
            EntityUtils.consume(respEntity);
        } finally {
            HttpClientUtils.closeQuietly(response);
            HttpClientUtils.closeQuietly(httpclient);
        }
        return returnData;
    }
    /**
     * get 请求
     * @param httpUrl
     * @param parameters
     * @param isRepeat
     * @param isReturnCookies
     * @param headers
     * @return
     * @throws IOException
     */
    private static String doGetHttpRequest(String httpUrl, Map<String, String> parameters, boolean isRepeat, boolean isReturnCookies , Header ...headers)throws IOException {
        String appendParametersStr  =  buildQuery(parameters);
        if(StringUtils.isNotBlank(appendParametersStr)) {
            httpUrl = httpUrl+"?"+appendParametersStr;
        }
        String returnDatas = null;
        returnDatas = httpGetConnection(httpUrl,true, headers);
        if(StringUtils.isBlank(returnDatas)) {
            if(isRepeat) {
                AtomicInteger integer = new AtomicInteger(0);
                while(repeatTime != integer.get()) {
                    System.out.println("正在重试第["+(integer.incrementAndGet())+"]次~");
                    returnDatas = httpGetConnection(httpUrl,true, headers);
                    if(StringUtils.equals(returnDatas, DEFAULT_STR_MARK) || StringUtils.isNotBlank(returnDatas)) {
                        break;
                    }
                }
            }
        }
        return StringUtils.equals(returnDatas, DEFAULT_STR_MARK) ? null : returnDatas;
    }

    /**
     * 创建Http连接请求
     * @param httpUrl
     * @param parameters
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static CloseableHttpClient getHttpClient(String httpUrl, Map<String, String> parameters) throws ClientProtocolException, IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String appendParametersStr  =  buildQuery(parameters);
        if(StringUtils.isNotBlank(appendParametersStr)) {
            httpUrl = httpUrl+"?"+appendParametersStr;
        }
        HttpGet httpGet = new HttpGet(httpUrl);
        //httpGet.setConfig(requestConfig);
        httpClient.execute(httpGet);
        return httpClient;
    }


    private static String httpGetConnection(String httpUrl,boolean isReturnCookies,Header[] headers) throws IOException{
        BasicCookieStore cookieStore = new BasicCookieStore();
        CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        HttpGet httpGet = new HttpGet(httpUrl);
        httpGet.setConfig(requestConfig);
        String returnData = null;
        HttpEntity respEntity = null;
        CloseableHttpResponse responsere = null;
        if(headers != null && headers.length > 0) {
            httpGet.setHeaders(headers);
        }
        try {
            responsere = httpclient.execute(httpGet);
            respEntity = responsere.getEntity();
            if(respEntity != null) {
                returnData = EntityUtils.toString(respEntity, DEFAULT_CHARSET);
                System.out.println("HTTP 请求 Response == >" +returnData);
                //Http 返回状态码 200 表示请求成功
                if(responsere.getStatusLine().getStatusCode() >= 200 && responsere.getStatusLine().getStatusCode() < 300) {
                    // 如果请求成功 就算没有数据 也算成功 则表示不需要重试.标志加入
                    if(StringUtils.isBlank(returnData)) {
                        returnData = DEFAULT_STR_MARK;
                    }
                }else {
                    returnData = null;
                }
            }
            EntityUtils.consume(respEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            HttpClientUtils.closeQuietly(responsere);
            HttpClientUtils.closeQuietly(httpclient);
        }
        return returnData;
    }
    /**
     * 请求参数拼装
     * 默认UTF-8字符集
     * 默认参数加密
     * @Title: buildQuery
     * @param params
     * * @return
     * @throws IOException
     */
    public static String buildQuery(Map<String, String> params) throws IOException {
        return buildQuery(params, DEFAULT_CHARSET,true);
    }
    /**
     *加密请求参数
     * @param params 参数
     * @param charset 加密编码
     * @param isEncoding 是否加密
     * @return
     * @throws IOException
     */
    public static String buildQuery(Map<String, String> params, String charset,boolean isEncoding) throws IOException {
        if (MapUtils.isEmpty(params)) {
            return null;
        }
        StringBuilder query = new StringBuilder();
        Set<Entry<String, String>> entries = params.entrySet();
        boolean hasParam = false;
        for (Entry<String, String> entry : entries) {
            String name = entry.getKey();
            String value = entry.getValue();
            // 忽略参数名或参数值为空的参数
            if (StringUtils.isNotBlank(name)&&StringUtils.isNotBlank(value)) {
                if (hasParam) {
                    query.append("&");
                } else {
                    hasParam = true;
                }
                query.append(name).append("=").append((isEncoding ? URLEncoder.encode(value, charset):value));
            }
        }
        return query.toString();
    }

    public static void main(String[] args) {
        try {
            System.out.println(doGet("http://www.baidu.com"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
