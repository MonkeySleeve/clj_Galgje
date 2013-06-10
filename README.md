galgje
=========
A simple server based game built with
[Ring](https://github.com/ring-clojure),
[Compojure](https://github.com/weavejester/compojure),
[lib-noir](https://github.com/noir-clojure/lib-noir) and
[Hiccup](https://github.com/weavejester/hiccup). The project template
was borrowed from [Luminus](http://www.luminusweb.net/).

# Download and run:

    $ git clone git@github.com:borkdude/galgje.git
    $ cd galgje
    $ lein ring server

A browser window will open and you'll be able to play.

# Run tests:

    $ lein test

# Deploy to Heroku:

    $ heroku create --stack cedar
    $ git push heroku master

More, see [Heroku](https://blog.heroku.com/archives/2011/7/5/clojure_on_heroku).

# TODO or student assignments

* Make form where a word can be insert
* Word needs to be check for length, characters etc
* Pictures of hangman for different stages need to be made
* Check for how many turns are left
* Implement Easter egg
* layout/design needs to be created
