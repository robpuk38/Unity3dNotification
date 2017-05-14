using System;
using UnityEngine;
using UnityEngine.UI;

public class AndroidManager : MonoBehaviour {

    public Text DebugText;

    private static string AndroidClassPackage = "com.pctrs.unitynotification.UnityNotifincationsActivity";
    private static string MainUnityNativeActivity = "com.unity3d.player.UnityPlayerNativeActivity";


    //sending from unity client to android plugin class with a callback to our unity client using the mainunitynaticeactivey
    
    void Start () {
        DebugText.text = "We are Live";
        SendNotification(200);

    }

    //sending from client A
    public static void SendNotification(int id)
    {
        Debug.Log("MYAPPTEST" + " WE ARE SENDING A NOTIFICATION THOUGH ");

        AndroidJavaClass OurNotificationsPlugin;

        try
        {
            OurNotificationsPlugin = new AndroidJavaClass(AndroidClassPackage);
            
            if(OurNotificationsPlugin != null)
            {
                // we are even better to go now becaue it has access
                
                String SIconImage = "notify_icon_big";
                String LIconImage = "notify_icon_big";
                Color32 bgColor;
                int executeMode = 2;
                bool sound = true;
                bool lights = true;
                bool vibration = true;
                String title = "MY TITLE";
                String message = "My Messsage";
                String ticker = message;
                long delaytime = 50;
                Debug.Log("MYAPPTEST" + "  we are even better to go now becaue it has access ");
                OurNotificationsPlugin.CallStatic("GetNotification", id, delaytime * 1000L, title,  message, ticker, sound ? 1 : 0, vibration ? 0 : 1, lights ? 0 : 1, SIconImage, LIconImage, 000, executeMode, MainUnityNativeActivity);
            }
            else
            {
                //damit we have a error again but its not from the android java calss its we have a null
                Debug.Log("MYAPPTEST" + "  damit we have a error again but its not from the android java calss its we have a null ");
            }

         // hey we are good to go lets do it
        }
        catch(AndroidJavaException ex)
        {
            // fuck we crashed
            Debug.Log("MYAPPTEST" + "   fuck we crashed " + ex.Message);
        }
        catch (Exception ex)
        {
            // dam we crashed from some exeption 
            Debug.Log("MYAPPTEST" + "   dam we crashed from some exeption  " + ex.Message);
        }

    }
    //reciveing from client someother like click B
    public static void GetNotification()
    {
        Debug.Log("MYAPPTEST" + " WE ARE GETTING A NOTIFICATION PASS ");
    }

    private void OnApplicationPause(bool pause)
    {
        if(pause)
        {
            Debug.Log("MYAPPTEST" + " WE ARE PAUSED AS TRUE");
        }
        else
        {
            Debug.Log("MYAPPTEST" + " WE ARE NOT PAUSED SO ITS  FALSE");
        }
        
    }

    private void OnApplicationQuit()
    {
        Debug.Log("MYAPPTEST" + " WE HAVE QUIT THE APP");
    }

    private void OnApplicationFocus(bool focus)
    {
        if(focus)
        {
            // this is true
            DebugText.text = "WE ARE FOUCSED THIS MEANS WE ARE IN THE APP ";
            Debug.Log("MYAPPTEST" + " WE ARE FOUCSED THIS MEANS WE ARE IN THE APP");
        }
        else
        {
            // this is false
            SendNotification(300);
            DebugText.text = "WE ARE NOT FOUCSED THIS MEANS WE ARE IN THE APP ";
            Debug.Log("MYAPPTEST" + " WE ARE NOT FOUCUSED MEANS WE ARE IN THE BACKGROUND BUT STILL RUNNING");
        }
    }

    // Update is called once per frame
    void Update () {
		
	}
}
