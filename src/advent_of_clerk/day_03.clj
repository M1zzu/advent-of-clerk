;; # 🎄 Advent of Clerk: Day 3
(ns advent-of-clerk.day-03
  (:require [nextjournal.clerk :as clerk]
            [clojure.java.io :as io]
            [clojure.string :as str]))

; in day 3, let's validate rucksacks
;; boing boing

^::clerk/no-cache
(def input (slurp (io/resource "day-03.txt")))

(def lines (str/split-lines input))

;; are all of these even even-numbered?

(map #(even? (count %)) lines)

;; yes 😎

(def split-lines (map #(vector (subs % 0 (/ (count %) 2))
                               (subs % (/ (count %) 2))) lines))

;; test, looping through two strings:
(defn find-common-char [str1 str2]
  (loop [string str2]
    (if (.contains str1 (str (first string)))
      (first string)
      (recur (rest string)))))

;; items in the rucksacks

(def common-item-types (map #(find-common-char (first %) (second %)) split-lines))

(def item-types (concat (seq "abcdefghijklmnopqrstuvwxyz")
                        (seq "ABCDEFGHIJKLMNOPQRSTUVWXYZ")))

(def priorities (into (sorted-map) (map vector
                                        item-types
                                        (range 1 (+ 1 (count item-types))))))

(reduce + (map priorities common-item-types))

;; ## Part Two

(def groups (partition 3 lines))

(defn find-common-chars [str1 str2 str3]
  (->> (for [c1 str1]
         (if (.contains str2 (str c1))
           (if (.contains str3 (str c1))
             c1
             )))
       (remove nil?)
       (distinct)
       (first)))

(def badges (map #(apply find-common-chars %) groups))

(reduce + (map priorities badges))