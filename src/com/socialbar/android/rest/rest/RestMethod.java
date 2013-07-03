package com.socialbar.android.rest.rest;

import com.socialbar.android.rest.resource.Resource;

public interface RestMethod<T extends Resource>{

	public RestMethodResult<T> execute();
}
