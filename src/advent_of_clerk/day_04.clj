;; # 🎄 Advent of Clerk: Day 4
(ns advent-of-clerk.day-04
  (:require [nextjournal.clerk :as clerk]
            [clojure.java.io :as io]
            [clojure.string :as str]))

^::clerk/no-cache
(def input (slurp (io/resource "day-04.txt")))

(def parsed-ish (as-> input v (str/split-lines v)
                      (map #(str/split % #",") v)))

;; How to parse? Like this:
(as-> "2-4" v (str/split v #"-")
      {:from (parse-long (first v)) :to (parse-long (second v))})

(defn parse-range [range-str]
  (as-> range-str v
        (str/split v #"-")
        {:from (parse-long (first v))
         :to (parse-long (second v))}))

(def parsed (map #(map parse-range %) parsed-ish))

(defn is-contained [range1 range2]
  (and (>= (:from range1) (:from range2))
       (<= (:to range1) (:to range2))))

(defn contained [pair]
  (or (is-contained (first pair) (second pair))
      (is-contained (second pair) (first pair))))

(count (filter identity (map contained parsed)))

;; # Part 2

;; Wo gibt's überhaupt eine Überlappung?

(defn is-overlapping [range1 range2]
  (or (<= (:from range2) (:from range1) (:to range2))
      (<= (:from range2) (:to range1) (:to range2))))

; not very beautiful, the previous function should cover this instead.

(defn overlaps [pair]
  (or (is-overlapping (first pair) (second pair))
      (is-overlapping (second pair) (first pair))))

(count (filter identity (map overlaps parsed)))