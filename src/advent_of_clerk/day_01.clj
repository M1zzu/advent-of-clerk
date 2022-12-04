;; # 🎄 Advent of Clerk: Day 1
(ns advent-of-clerk.day-01
  (:require [nextjournal.clerk :as clerk]
            [clojure.java.io :as io]
            [clojure.string :as str]))

;; Hello Clerk.

^::clerk/no-cache
(def input (slurp (io/resource "day1.txt")))

(def calories (->> (str/split-lines input)
                   (mapv parse-long)
                   (partition-by nil?)
                   (remove #(= '(nil) %))
                   (map #(reduce + %))))

(apply max calories)

(reduce + (take 3 (reverse (sort calories))))