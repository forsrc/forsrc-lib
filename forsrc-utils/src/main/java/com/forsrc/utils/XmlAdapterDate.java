package com.forsrc.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * The type Xml adapter date.
 */
public class XmlAdapterDate extends XmlAdapter<String, Date> {

    private static final ThreadLocal<SimpleDateFormat> TL = new ThreadLocal<>();

    @Override
    public Date unmarshal(String v) throws Exception {
        return getSimpleDateFormat().parse(v);
    }

    @Override
    public String marshal(Date v) throws Exception {
        return getSimpleDateFormat().format(v);
    }

    private SimpleDateFormat getSimpleDateFormat(){
        SimpleDateFormat sdf = TL.get();
        if(sdf == null){
            synchronized(XmlAdapterDate.class){
                if(sdf == null){
                    sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    TL.set(sdf);
                    return sdf;
                }
            }
        }
        return sdf;
    }
}
