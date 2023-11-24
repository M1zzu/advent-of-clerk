;; # 🎄 Advent of Clerk: Day 6
(ns advent-of-clerk.day-06
  (:require
    [clojure.java.io :as io]
    [nextjournal.clerk :as clerk]))

^::clerk/no-cache
(def input (slurp (io/resource "day-06.txt")))

(distinct? \a \a)
(distinct? \a \b)

(take 4 input)

(apply distinct? (take 4 input))

(range (count input))

(defn find-index [input]
  (loop [input input
         index 0]
    (if (apply distinct? (take 4 input))
      (+ index 4)
      (recur (rest input) (+ 1 index)))))

;; # Part Two

(defn find-index2 [input]
  (loop [input input
         index 0]
    (if (apply distinct? (take 14 input))
      (+ index 14)
      (recur (rest input) (+ 1 index)))))