package com.wendy.instagramviewer.api.ClientUtils;

import com.wendy.instagramviewer.api.ClientNewBasic.HttpInvoker;

public abstract class Client
{
    protected HttpInvoker hi;
    protected String req_url, file_name, file_path;

    public abstract String perform();
}
