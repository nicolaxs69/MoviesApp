# MoviesApp

This is a movies app, where you can search the latest movies from hollywood. User can filter by category and see detail for every movie.

## Functionalities

- User can navigate on movies in a list.
- Movies are separated by three main categories (popular, top rated, upcoming).
- By Clicking on an movie user can see more detail about the selected movie of interest.
- For now the app only have 1 mode (online). In Online mode items are obtained from The Movie Database API (https://www.themoviedb.org/documentation/api).
- User can filter movies by name and rating.

## Architecture, Structure and Patterns

 The app was developed using the MVP (Model View Presenter) architecture with three layers:
- Model
- View
- Presenter

## Project Structure.

### Model Package  
Has the Models data layer.
MovieResponse models API response.

### UI Package
Every folder inside are separated by movie category which includes Activities, presenter, adapters and view methods and also the detail movie screen.

### Utils Package
Contains networking handling classes. 
Contains utility classes, functions and constants for every part of the app.

## Libraries
- Networking (Retrofit 2).
- Parsing data (Gson).
- Image Loading and Cache (Glide).
- DialogAlerts (FancyGifDialog-Android).
- Animations (Lottie).

## Practices
1) The single responsibility principle is the first one of the SOLID principles, it means that every module (piece of code)  should have responsability for one, and only one purpose or functionality provided by software.

2) A clean code is characterized by the implementation of a component architecture that allows to organize the code in a standard way, especially for scalable projects.

- It must be legible, which means that the declaration of variables and methods have a concordance and allows any developer to understand it, such as reading a book.

- You must use some of the SOLID principles, such as the one mentioned above.

- Classes should be as simple as possible in order to be testable, and depending on the programmer's expertise use recursive functions (Big O Notation)

- And one of the most important, to have documentation so that any programmer can work on the project more efficiently and can be improved collaboratively by the community.

## Authors

* **Nicolas Escobar Cruz**


