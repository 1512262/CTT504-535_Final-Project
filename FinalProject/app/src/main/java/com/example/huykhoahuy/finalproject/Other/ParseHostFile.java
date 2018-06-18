package com.example.huykhoahuy.finalproject.Other;

import android.content.Context;
import android.content.res.XmlResourceParser;

import com.example.huykhoahuy.finalproject.Class.LotteryCompany;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class ParseHostFile {
    public ArrayList<LotteryCompany> lotteryCompanies;

    public ParseHostFile(Context context, int id) {
        lotteryCompanies = new ArrayList<>();
        parseXML(context,id);
    }

    public void parseXML(Context context, int id) {
        /*
        XmlPullParserFactory parserFactory;
        try{
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();
            InputStream is = context.getAssets().open(filename);
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES,false);
            processParsing(parser);

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
        XmlResourceParser xrp = context.getResources().getXml(id);
        LotteryCompany item = null;
        try {
            xrp.next();
            int eventType = xrp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        if (xrp.getName().compareTo("host") == 0) {
                            item = new LotteryCompany();
                        } else if (xrp.getName().compareTo("province_id") == 0) {
                            xrp.next();
                            item.setProvince_id(xrp.getText());
                        } else if (xrp.getName().compareTo("name") == 0) {
                            xrp.next();
                            item.setName(xrp.getText());
                        } else if (xrp.getName().compareTo("address") == 0) {
                            xrp.next();
                            item.setAddress(xrp.getText());
                        } else if (xrp.getName().compareTo("phone") == 0) {
                            xrp.next();
                            item.setPhone(xrp.getText());
                        } else if (xrp.getName().compareTo("latitude") == 0) {
                            xrp.next();
                            item.setLatitude(xrp.getText());
                        } else if (xrp.getName().compareTo("longitude") == 0) {
                            xrp.next();
                            item.setLongitude(xrp.getText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (xrp.getName().compareTo("host") == 0) {
                            lotteryCompanies.add(item);
                            item = null;
                        }
                }
                /*
                if(eventType == XmlPullParser.START_DOCUMENT)
                {
                    //stringBuffer.append("--- Start XML ---");
                }
                else if(eventType == XmlPullParser.START_TAG)
                {
                    //stringBuffer.append("\nSTART_TAG: "+xpp.getName());
                }
                else if(eventType == XmlPullParser.END_TAG)
                {
                    //stringBuffer.append("\nEND_TAG: "+xpp.getName());
                }
                else if(eventType == XmlPullParser.TEXT)
                {
                    //stringBuffer.append("\nTEXT: "+xpp.getText());
                }
                */
                xrp.next();
                eventType = xrp.getEventType();
            }
            //stringBuffer.append("\n--- End XML ---");
            //return stringBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processParsing(XmlPullParser parser) throws XmlPullParserException, IOException {
        lotteryCompanies = new ArrayList<>();
        int eventType = parser.getEventType();
        LotteryCompany currentCompany = null;

        while(eventType!=XmlPullParser.END_DOCUMENT){
            String eltName = null;

            switch (eventType){
                case XmlPullParser.START_TAG:
                    eltName = parser.getName();

                    if("host".equals(eltName)){
                        currentCompany = new LotteryCompany();
                        lotteryCompanies.add(currentCompany);
                    }else if (currentCompany!=null){
                        if("location".equals(eltName))
                        {
                            if("latitude".equals(eltName)){
                                currentCompany.setLatitude(eltName);
                            }else if ("longitude".equals(eltName)){
                                currentCompany.setLongitude(eltName);
                            }
                        }else if ("phone".equals(eltName)){
                            currentCompany.setPhone(eltName);
                        }else if ("province_id".equals(eltName)){
                            currentCompany.setProvince_id(eltName);
                        }else if ("name".equals(eltName)){
                            currentCompany.setName(eltName);
                        }else if ("address".equals(eltName)){
                            currentCompany.setAddress(eltName);
                        }




                    }
                    break;
            }
            eventType = parser.next();
        }
    }
}
