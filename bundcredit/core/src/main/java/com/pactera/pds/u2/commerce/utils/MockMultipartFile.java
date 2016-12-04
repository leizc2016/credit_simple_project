package com.pactera.pds.u2.commerce.utils;

import java.io.*;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

public class MockMultipartFile
    implements MultipartFile
{

    public MockMultipartFile(String name, byte content[])
    {
        this(name, "", null, content);
    }

    public MockMultipartFile(String name, InputStream contentStream)
        throws IOException
    {
        this(name, "", null, FileCopyUtils.copyToByteArray(contentStream));
    }

    public MockMultipartFile(String name, String originalFilename, String contentType, byte content[])
    {
        Assert.hasLength(name, "Name must not be null");
        this.name = name;
        this.originalFilename = originalFilename == null ? "" : originalFilename;
        this.contentType = contentType;
        this.content = content == null ? new byte[0] : content;
    }

    public MockMultipartFile(String name, String originalFilename, String contentType, InputStream contentStream)
        throws IOException
    {
        this(name, originalFilename, contentType, FileCopyUtils.copyToByteArray(contentStream));
    }

    public String getName()
    {
        return name;
    }

    public String getOriginalFilename()
    {
        return originalFilename;
    }

    public String getContentType()
    {
        return contentType;
    }

    public boolean isEmpty()
    {
        return content.length == 0;
    }

    public long getSize()
    {
        return (long)content.length;
    }

    public byte[] getBytes()
        throws IOException
    {
        return content;
    }

    public InputStream getInputStream()
        throws IOException
    {
        return new ByteArrayInputStream(content);
    }

    public void transferTo(File dest)
        throws IOException, IllegalStateException
    {
        FileCopyUtils.copy(content, dest);
    }

    private final String name;
    private String originalFilename;
    private String contentType;
    private final byte content[];
}
