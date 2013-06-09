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

(defn turn-page [button-pressed]
	; (model/new-state)
  
	;       rownr (Integer/parseInt (str (second button-id)))
	;       colnr (Integer/parseInt (str (nth button-id 2)))]
 
	(model/play! [button-pressed])
	(view/play-screen)
 )
(defn letter-submit [input-params]
	; (model/new-state)
	; (let [button-id (name (first (keys input-params)))
	;       rownr (Integer/parseInt (str (second button-id)))
	;       colnr (Integer/parseInt (str (nth button-id 2)))]
	(model/add-char-guessed (get input-params :guess))
	(model/play!)
	(view/play-screen)
		; (if-let [winner (model/winner?)]
		;   (view/winner-screen winner)
		;   (if (model/full-board?)
		;     (view/draw-screen)
		;     (view/play-screen)
)

(defroutes galgje-routes
	(GET "/" [] (start-page))
	(POST "/" {input-params :params}
		(letter-submit input-params)
	)
)