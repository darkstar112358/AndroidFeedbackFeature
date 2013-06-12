package com.example.feedback_test_3;

import android.app.ActivityManager;

public class SendFeedback extends MainActivity {

    private String feedbackBody;

    public SendFeedback(String feedbackBodyValue)
    {
        feedbackBody = feedbackBodyValue;
    }

    public String send() {
        StringBuilder feedbackBodyStringBuilder = new StringBuilder();
        feedbackBodyStringBuilder.append("Sent by : ");

        if(StateFlags.sendAsAnonymous)
            feedbackBodyStringBuilder.append("Anonymous");
        else
            feedbackBodyStringBuilder.append(DeviceData.userId);

        feedbackBodyStringBuilder.append("\n\nMessage : ");
        feedbackBodyStringBuilder.append(feedbackBody);

        feedbackBodyStringBuilder.append("\n\nData:\nPackage name : " + DeviceData.packageName +"\nPackage version : "
                + DeviceData.packageVersion + "\nCurrent Date : "+DeviceData.currentDate+"\nDevice : "+DeviceData.device+"\nSdk Version : "+DeviceData.sdkVersion+
                "\nBuild Id : "+DeviceData.buildId+"\nBuild Release : "+DeviceData.buildRelease+"\nBuild Type : "+DeviceData.buildType+"\nBuild fingerprint : "+DeviceData.buildFingerPrint
                +"\nBrand : "+DeviceData.brand+"\nPhone type : "+DeviceData.phoneType+"\nNetwork Type : "+DeviceData.networkType+"\n");

        if(StateFlags.includeSystemDataCheck)
        {
            feedbackBodyStringBuilder.append("\n" + "List of Running Activities: ");
            for(ActivityManager.RunningAppProcessInfo iterator : DeviceData.runningApps)
            {
                feedbackBodyStringBuilder.append("\n");
                feedbackBodyStringBuilder.append(iterator.processName);
            }
            feedbackBodyStringBuilder.append("\n\nSystem Log:\n");
            feedbackBodyStringBuilder.append(DeviceData.systemLog);

            feedbackBodyStringBuilder.append("\n\nEvents Log:\n");
            feedbackBodyStringBuilder.append(DeviceData.eventsLog);

        }

        return feedbackBodyStringBuilder.toString();

    }

}