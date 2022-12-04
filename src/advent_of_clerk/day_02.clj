;; # 🎄 Advent of Clerk: Day 2
(ns advent-of-clerk.day-02
  (:require [nextjournal.clerk :as clerk]
            [clojure.java.io :as io]
            [clojure.string :as str]))

;; day 2 it is. We're apparently playing rock paper scissors.

^::clerk/no-cache
(def input (slurp (io/resource "day-02.txt")))

(def structured (map #(str/split % #" ") (str/split-lines input)))

(def mapping {"A" :rock "X" :rock
              "B" :paper "Y" :paper
              "C" :scissors "Z" :scissors})

(def mapped (map #(vector (mapping (first %))
                          (mapping (second %))) structured))

(defn game-result [my-hand their-hand]
  (case my-hand
    :rock (case their-hand
            :rock :draw
            :paper :lose
            :scissors :win)
    :paper (case their-hand
             :rock :win
             :paper :draw
             :scissors :lose)
    :scissors (case their-hand
                :rock :lose
                :paper :win
                :scissors :draw)))

(defn score-round [my-hand their-hand]
  (let [result (game-result my-hand their-hand)
        pick-points (case my-hand :rock 1 :paper 2 :scissors 3)
        game-points (case result :lose 0 :draw 3 :win 6)]
    (+ pick-points game-points)))

(def verbose-scores (map #(vector (score-round (second %) (first %)) (second %) (first %)) mapped))

(def scores (map #(score-round (second %) (first %)) mapped))

(reduce + scores)

;; ## Part Two

(defn correct-turn [their-hand ideal-move]
  (case  their-hand
    :rock (case ideal-move
            :draw :rock
            :lose :scissors
            :win :paper)
    :paper (case ideal-move
             :lose :rock
             :draw :paper
             :win :scissors)
    :scissors (case ideal-move
                :lose :paper
                :win :rock
                :draw :scissors)    ))

(def mapping-2 {"A" :rock "X" :lose
              "B"   :paper "Y" :draw
              "C"   :scissors "Z" :win})

(def mapped-2 (map #(replace mapping-2 %) structured))

(def strategy-2 (map #(vector (first %)
                            (correct-turn (first %) (second %))) mapped-2))

(def scores-2 (map #(score-round (second %) (first %)) strategy-2))

(reduce + scores-2)