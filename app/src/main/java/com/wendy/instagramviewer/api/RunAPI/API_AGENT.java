package com.wendy.instagramviewer.api.RunAPI;

import com.wendy.instagramviewer.api.ClientUtils.Client;
import com.wendy.instagramviewer.api.StaticTags.API_Tags;

public class API_AGENT extends Thread
{
    private Client api_agent;
    private String res_agent;

    public API_AGENT(Client api_client)
    {
        api_agent = api_client;
        res_agent = API_Tags.RTN_FAIL;
    }

    public void run()
    {
        res_agent = api_agent.perform();
    }

    public String perform()
    {
        this.start();
        try
        {
            this.join();
        }
        catch (Exception e)
        {
            System.out.println("Joining Process Error Found");
        }
        return res_agent;
    }
}
