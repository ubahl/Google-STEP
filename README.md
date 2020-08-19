# Google Student Training in Engineering Program

This repo contains my project from the first half of STEP.
This work is based on the [Google Software Product Sprint](https://g.co/softwareproductsprint) program.

## Boba Bud Website

### About

Boba Bud is a fun [website](https://umabahl-step-2020.uc.r.appspot.com/) for boba lovers to find nearby tea places! 

### Technologies used

The web application was created using HTML, CSS, Javascript, and Java Servlets. It is built using Maven.

### APIs used

* Google Datastore API: for storing and retrieving user comments
* Google Nearby Places API: for searching for nearby tea stores and accessing basic details on the stores
* Google UserService API: for allowing the user to log in/out
* Google Maps Javascript API: for displaying dynamic maps of each store's location

### User Flow:

From the homepage, the user searches for a drink:
![homepage](https://user-images.githubusercontent.com/48428336/90696240-c2a32200-e230-11ea-91b2-3332f733c177.png)

and are presented with many stores nearby which offer the drink:
![search1](https://user-images.githubusercontent.com/48428336/90696310-df3f5a00-e230-11ea-8c3e-0be47d94daed.png)
![search2](https://user-images.githubusercontent.com/48428336/90696290-d77fb580-e230-11ea-9181-94c57c8cfcea.png)

Once the users chooses a store, they can view it's rating, whether it is open right now, and the location on a dynamic map:
![storeinformation](https://user-images.githubusercontent.com/48428336/90696329-e9f9ef00-e230-11ea-9d15-22b445bf1bf7.png)


