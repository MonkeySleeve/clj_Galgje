(ns galgje.model
	(:require [noir.session :as session])
)

(def init-state {
	:total-guesses 1
	:word ""
})

(def chars-guessed (atom #{}))

(defn get-guessed-characters []
	(apply str (interpose ", " @chars-guessed))
)

(defn reset-game! []
	(session/put! :game-state init-state)
  (reset! chars-guessed #{})
)

(defn get-total-guesses []
	(:total-guesses (session/get :game-state))
)

(defn get-word []
	(:word (session/get :game-state))
)

(defn is-char-in-word? [char-guessed]
	(some #{char-guessed} (get-word))
)

(defn guessed-character? [char-guessed]
	(@chars-guessed char-guessed)
)

(defn get-remaining-characters []
	(->>
		(for [c (get-word)]
			(if (guessed-character? c)
				c
				\_
			)
		)
		(apply str)
	)
)

(defn get-word-vector []
	(clojure.string/split (get-word) #"")
)

(defn inc-total-guesses [old-state]
	(update-in old-state [:total-guesses] inc)
)

(defn new-word [word old-state]
	(update-in old-state [:word] word)
)

(defn add-char-guessed [char-guessed]
  (if-not(= char-guessed "")
    (when-not (@chars-guessed char-guessed)
      (swap! chars-guessed conj char-guessed)
	)
 )
)

(defn draw-hangman! []
	(session/swap!
		(fn [session-map]
			(update-in session-map [:game-state] inc-total-guesses)
		)
	)
)

(defn set-word! [word]
	(session/swap!
		(fn [session-map]
			(update-in session-map [word :game-state] new-word)
		)
	)
)

(defn winner? []
	(not(some #{\_} (get-remaining-characters)))
)

(defn loser? []
	(= (get-total-guesses) 12)
)