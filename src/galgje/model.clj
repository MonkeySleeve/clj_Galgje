(ns galgje.model
	(:require [noir.session :as session]))

(def init-state {:total-guesses 1 :word "winner"})

(def chars-guessed (
	atom ["a" "b" "c" "c" "c" "c" "c" "c" "c" "c" "c" "c" "c" "c" "c" "c" "c" "c" "c" "c" "c" ])
)

(defn get-guessed-characters []
	(apply str (interpose ", " @chars-guessed))
)

(defn reset-game! []
	(session/put! :game-state init-state)
)

(defn get-total-guesses []
	(:total-guesses (session/get :game-state))
)

(defn get-word []
	(:word (session/get :game-state))
)

(defn winner?
	"checks if there is a winner. when called with no args, checks for player X and player O.
	returns the character for the winning player, nil if there is no winner"
	([] (winner? (get-board)))
	([board player]
		(if (or (winner-in-rows? board player)
			(winner-in-cols? board player)
			(winner-in-diagonals? board player))
		player)
	)
)

(defn new-state [old-state]
	{
		:total-guesses (3 (:total-guesses old-state))
		:word (str "timo" (:word old-state))
	}
	old-state
)

(defn play! []
	(session/swap! (fn [session-map]
		(assoc session-map :game-state
			(new-state (:game-state session-map))
			)
		)
	)
)