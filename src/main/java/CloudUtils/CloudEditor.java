package CloudUtils;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.ArrayMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class CloudEditor
{
    private HttpRequestFactory requestFactory;
    private String baseURL = "https://todoserver222.herokuapp.com/";
    private String todosURL = baseURL + "todos/";
    private String owner = "team4";

    public CloudEditor()
    {
        requestFactory = new NetHttpTransport().createRequestFactory();
    }

    public int addTodoItem(String title) throws IOException
    {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("title", title);
        data.put("owner", this.owner);
        HttpContent content = new UrlEncodedContent(data);
        HttpRequest postRequest = requestFactory.buildPostRequest(
                new GenericUrl(todosURL), content);
        String rawResponse = postRequest.execute().parseAsString();

        var resultID = "";
        for (var ch : rawResponse.toCharArray())
        {
            if (Character.isDigit(ch))
            {
                resultID += ch;
            }
        }
        return Integer.parseInt(resultID);
    }


    public boolean deleteTodoItem(int id) throws IOException
    {
        HttpRequest deleteRequest = requestFactory.buildDeleteRequest(
                new GenericUrl(todosURL + id));
        try
        {
            String rawResponse = deleteRequest.execute().parseAsString();
        }
        catch (HttpResponseException hre)
        {
            return false;
        }
        return true;
    }


    public boolean updateTodoItem(int id, String title) throws IOException {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("id", id);
        data.put("title", title);
        data.put("owner", owner);
        HttpContent content = new UrlEncodedContent(data);
        HttpRequest putRequest = requestFactory.buildPutRequest(
                new GenericUrl(todosURL + id), content);
        try
        {
            String rawResponse = putRequest.execute().parseAsString();
        }
        catch (HttpResponseException hre)
        {
            return false;
        }

        return true;
    }
}