package com.zht.fielddownload.http;

import java.io.File;

public interface DownloadCallback {
    void success(File file);
    void fail(int errorcode,String errorMessage);
    void progress(int progress);
}
