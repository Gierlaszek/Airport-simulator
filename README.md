# Airport-simulator
Application showing sample operation with data. The data is downloaded from json-generator.com in the form of JSON. Then it is possible to perform several functionalities where the operation is described below 

## Tables of contents
* [Technologies](#technologies)
* [Setup](#setup)
* [User Guide](#user-guide)
* [Developing the application](#developping-the-application)

## Technologies 
The application was created in Android Studio 4.1 by use Java language. Android SDK with Android platform version 30. We use it to compile the app.

## Setup 
Application in this repository should be automatically importable, compilable, and runnable. Follow the instructions to install:
1. Clone this repository onto your computer: git clone 
2. Open Android Studio
3. Either press "Open an Existing Android Studio Project" or click File > Open...
4. Navigate to folder. Press Choose, or OK
5. Wait for the Gradle execution to complete. You should see "app" appear as one of the build configurations
6. Generate a new URL with a JSON file from the json-generator site. The file skeleton is given in the HowJSONShouldLookLike.
7. In the MainActivity file, the URL should be pasted in the designated place.
8. Run the application (Run > Run 'App'), choose either a connected device or a simulator to run on
9. When the application loads, you will see the initial window. 

## User Guide 
After starting the application, we will see the window below. The next window will appear when the data download from the server is completed.

<img width="200" alt="Zrzut ekranu 2021-05-19 o 09 21 11" src="https://user-images.githubusercontent.com/48731610/118779206-1c517f00-b88b-11eb-9e69-fade9772d024.png">

Next window:

<img width="200" alt="Zrzut ekranu 2021-05-19 o 09 21 19" src="https://user-images.githubusercontent.com/48731610/118779231-22dff680-b88b-11eb-9ac3-254e5cdf0617.png">

The user has to select the type of data of interest to him. They then choose to filter between an IATA airport code or flight number. He can also choose a date. The appropriate flight number or  IATA airport code will be automatically matched to the selected date.

An example of filtering the results:

<img width="200" alt="Zrzut ekranu 2021-05-19 o 09 21 49" src="https://user-images.githubusercontent.com/48731610/118779263-296e6e00-b88b-11eb-9e0c-f22d2bdcd52b.png">

<img width="200" alt="Zrzut ekranu 2021-05-19 o 09 22 05" src="https://user-images.githubusercontent.com/48731610/118779289-30957c00-b88b-11eb-8df7-e331e52952f2.png">

## Developing the application

 - Adding a new settings window where the user will be able to change the URL to JSON.

