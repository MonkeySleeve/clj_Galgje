(ns galgje.controller
	(:use compojure.core)
	(:require [compojure.core :as compojure]
		[galgje.view :as view]
		[galgje.model :as model]
	)
)

(defn start-page []
	(model/reset-game!)
	(view/start-screen)
)

(defn letter-submit [input-params]
  (model/add-char-guessed (first(get input-params :guess))) 
  
	(if-not(model/is-char-in-word? (first(get input-params :guess)))
		(model/draw-hangman!)
	)
	
	(if(model/winner?)
   (view/winner-screen)
   (if(model/loser?)
     (view/loser-screen) 
     (view/play-screen))
	)
)

(defn word-submit [input-params]
  (model/set-word! (get input-params :word)) 
  (view/play-screen)
)

(defroutes galgje-routes
	(GET "/" [] (start-page))
  (POST "/startgame" {input-params :params}
		(word-submit input-params)
	)
	(POST "/" {input-params :params}
		(letter-submit input-params)
	)
)