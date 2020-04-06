# SeniorAndroidCaseStudy

### Part 1

#### App Arcthitecture

The app is written in Kotlin and the architecture is MVVM (Model–view–viewmodel) with Koin dependency injection, Room entity database, Retrofit2 for api calls, coroutines for blocking/async calls, and androidx ui components.

<div align="center">
    <img src="/documents/final_architecture.png" height="700px"</img> 
</div>

//Launches app with Google Play Intent

adb shell am start -a com.android.vending.INSTALL_REFERRER --es referrer "https%3a%2f%2fm.alltheapps.org%2fget%2fapp%3fuserId%3dB1C92850-8202-44AC-B514-1849569F37B6%26implementationid%3dcl-and-erp%26trafficSource%3derp%26userClass%3d20200101"

<div align="center">
    <img src="/documents/driver_screen1.png" height="700px"</img> 
</div>

<div align="center">
    <img src="/documents/google_play_broadcast_screen.png" height="700px"</img> 
</div>

<div align="center">
    <img src="/documents/driver_screen2.png" height="700px"</img> 
</div>

<div align="center">
    <img src="/documents/database_list_screen.png" height="700px"</img> 
</div>


// Launches app with deeplink Intent

adb shell am start -W -a android.intent.action.VIEW -d "spigot://eng.dev/cs/product_info?id=12345\&trafficSource=deeplink" tov.com.seniorandroidcasestudy

<div align="center">
    <img src="/documents/driver_screen3.png" height="500px"</img> 
</div>

<div align="center">
    <img src="/documents/deeplink_screen.png" height="500px"</img> 
</div>

<div align="center">
    <img src="/documents/clipboard_alert.png" height="500px"</img> 
</div>

### Part 2

There are many options to accomplish mirroring local App and remote databases.

One could use a platform approach like Firebase's FireStore database for realtime data synchronization via underlying connected web sockets when the App is running and leverage Firebase Cloud Messenger (FCM) to trigger the App, when not running, to wake up and get new data when available from the server. When that App is running it will be subscribed to FireStore data updates and when they are published to the App it can write to it's local database. When the App writes data to it's local database it will also write data to FireStore via Firebase SDKs. The SDKs have built in logic to cache and sync as well to accommodate less than favorable network conditions. Amazon Amplify is a similar, but a more configurable option on the AWS platform. Since Firebase and Amplify use web-socket connections the interactions are much faster than Http restful interactions.

If using a pre-built proprietary platform is not an option, one could build web socket based APIS to handle realtime synchronization when the App is running. Using some sort of push service, like FCM, would be still be needed to trigger the App to wake up and reconnect when not running when new data is available on the remote database.

To deal with network loss and drops a good strategy is to employ a transactional scheme to ensure the App gets a confirmation of synchronization between App and remote database interactions. A basic example of this from the App perspective could be to a sync flag for each data object that is only set to true if there is a successful response from the api call request/response interaction. To sync efficiently from the server side the App would subscribe to all records with date-stamps newer then the newest local record stored in the local database.


