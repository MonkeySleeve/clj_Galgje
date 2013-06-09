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
	(if-not(model/is-char-in-word? (get input-params :guess))
		(model/draw-hangman!)
		(println "hang that man!")
	)
	(model/add-char-guessed (get input-params :guess))
	(view/play-screen)
	; (if (model/winner?)
	; 	(view/winner-screen)
	; )
	; (if (= model/get-total-guesses 13)
	; 	(view/loser-screen)
	; )
)

(defroutes galgje-routes
	(GET "/" [] (start-page))
	(POST "/" {input-params :params}
		(letter-submit input-params)
	)
)