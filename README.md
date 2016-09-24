# Wingz

Simple android proto-app using coarse device location. It is intended for Android 4.4.2 and above.

## Pre-requisites

* Android SDK v23
* Android Build Tools v23.0.2
* Android Support Repository

## Main features

* Displays a List of nearby sites that a given vehicle(car, plane, train) came across
* Shows detailled content about one site

## Overview

Here are what the mobile is presently doing. For more details, you can get to the source code directly.
For more information, check the [activity lifecycle](http://developer.android.com/reference/android/app/Activity.html) and [fragment lifecycle](http://developer.android.com/guide/components/fragments.html).
This app comes with a [pre-loaded](http://stackoverflow.com/questions/513084/how-to-ship-an-android-application-with-a-database) SQLite database containing data about interesting points on a given itinerary.
The picture are also pre-loaded in an asset folder. 

### Location 

The app launches automatic location updates so it can provide content-aware data.
Fastest and normal update intervals are setted by default between 5 and 10 seconds. The app should rely only on GPS connectivity to get the current location.

### Activity Package 

#### ScrollingActivity

This activity contains one fragment i.e ItemFragment for the list of interesting points on a given itinerary.
It has one Collapsing toolbar whose behaviour is almost the same as Twitter Android app.
It implements GoogleApiClient interfaces to [fetch the location](http://developer.android.com/training/location/receive-location-updates.html) and update ItemFragment content.

#### ItemFragment

This fragment displays a list with a picture and a description content for each list item. The normal behaviour after loading the database is to initialize a list of interesting points (called Sites) and update the list according to the device location.
Hence, this fragment has a onNewLocation method implemented by the ScrollingActivity. Whenever the location is updated, the ScrollingActivity sends the new location to the ItemFragement.
Then according to the new location, the currentList containing the actual list displayed updates.
For each item displayed in the List, the image is loaded from the asset folder that contains all the images needed. I used this tutorial to [read the image from the asset folder](https://xjaphx.wordpress.com/2011/10/02/store-and-use-files-in-assets/).
If the app is not displayed, a [notification](http://developer.android.com/guide/topics/ui/notifiers/notifications.html) can be triggered when a new site is discovered.

#### MyItemRecyclerViewAdapter

This Adapter manages how the list of interesting points i.e sites behave. It's basically the link between a set of rough data and their presentation in fragment_item_list layout.
For this, this adapter only a needs a Site List and a listener to be instantiated.

#### ContentActivity

It displays detailled description and a picture related to one site. The contentActivity is displayed once the user clicks on one item.
Some work could be enhanced about the design as for this [behaviour](http://techcrunch.com/2013/04/18/yahoos-surprisingly-gorgeous-new-ios-weather-app-centers-around-crowdsourced-photos/) that I did not get the time to code.
This [link](http://nicolaspomepuy.fr/blur-effect-for-android-design/) provides a way to implement the Yahoo Weather effect.

### Model Package

This is the layer that modelize the data stored in the SQLite database.

#### Site

A site is an interesting point on a given itinerary. The minimum required information are the location, and the content relative to a site.

It is basically an object with the following attributes:

* id(int) : Unique integer for each site
* title(String): Denotes the the title of a given site.
* type(String): Precises the of site, it could be a city, a monument, a park, a restaurant, etc
* content(String): The full description of a site. The user can get historic dates, linked services of a given site.
* latitude(Double): Location of the site
* longitude(Double): Location of a site
* radius(Double): Rough radius of a circle containing the whole site
* events(String): The message notifying the user of the new discovered site.

The Site Class has getter and setter of each of its attributes. 

#### Destination

A Destination is intended to acknowledge the user about transportation mean available when he arrives in a given city.

### Storage Package

This is the layer implementing the DAO layer. It relies on a third-party [Android Helper](https://github.com/jgilfelt/android-sqlite-asset-helper) class to manage database creation and version management using an application's raw asset files that provides developers with a simple way to ship their Android app with an existing SQLite database (which may be pre-populated with data) and to manage its initial creation and any upgrades required with subsequent version releases.

#### DatabaseOpenHelper

SQLiteAssetHelper is intended as a drop in alternative for the framework's [SQLiteOpenHelper](https://developer.android.com/reference/android/database/sqlite/SQLiteOpenHelper.html). Please familiarize yourself with the behaviour and lifecycle of that class.

Extend `SQLiteAssetHelper` as you would normally do `SQLiteOpenHelper`, providing the constructor with a database name and version number:

```java
public class MyDatabase extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "northwind.db";
    private static final int DATABASE_VERSION = 1;

    public MyDatabase(Context context) {
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
```

For more information, refer to the [documentation](https://github.com/jgilfelt/android-sqlite-asset-helper/blob/master/README.markdown) and [this tutorial](http://www.javahelps.com/2015/04/import-and-use-external-database-in.html)

#### DatabaseAccess

This in an abstract class is intended to provide with the CRUD methods. For this it contains the names of the tables contained in the database.

Each model (i.e site and destination) has a corresponding class that implements with all the methods to create, read, update and delete data from the database.

* DestinationAccess
* SiteAccess

## More 

Some directions for further development:

* Implement a back-end that serves the apps: The app would pre-load the data needed throughout the trip just before on-boarding
* Design a user profile/authentification : some interesting points could be saved as favorite
* Add a map to easily track the trip and better data visualization
* Add social features: having personal space to manage trip, make some todo, synchronize with Agenda
* Add easter eggs : functions to make it awesome for the user, hidden game(s)
* Enhance the design

## License

• [Apache Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)


