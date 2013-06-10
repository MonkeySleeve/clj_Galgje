(ns galgje.controller
	(:use compojure.core)
	(:require [compojure.core :as compojure]
		[galgje.view :as view]
		[galgje.model :as model]
	)
)

(defn start-page []
	(model/reset-game!)
	(view/play-screen)
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

(defroutes galgje-routes
	(GET "/" [] (start-page))
  (GET "/reset" [] (start-page))
	(POST "/" {input-params :params}
		(letter-submit input-params)
	)
)