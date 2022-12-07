;; # 🎄 Advent of Clerk: Day 5
(ns advent-of-clerk.day-05
  (:require [nextjournal.clerk :as clerk]
            [clojure.java.io :as io]
            [clojure.string :as str]))

^::clerk/no-cache
(def input (slurp (io/resource "day-05-sample.txt")))

(let [input-partitioned (partition-by str/blank? (str/split-lines input))]
  (def stacks-str (reverse (nth input-partitioned 0)))
  (def instructions-str (nth input-partitioned 2)))

stacks-str

instructions-str

(def stacks
  (as-> (first stacks-str) v
        (str/split v #" ")
        (remove str/blank? v)
        (map parse-long v)
        (zipmap v (repeat []))))

(defn parse-stack-row [row-str]
  (map #(nth % 1) (partition 3 4 row-str)))

(def stack-rows (map parse-stack-row (rest stacks-str)))

(defn add-to-vector-in-map
  "assumes map to be a map where values are vectors and chr to be a char"
  [map key chr]
  (if (not (= \space chr))
    (assoc map key (conj (map key) chr))
    map))

(defn add-all-in-row [m row]
  (reduce
    (fn [m num-and-char]
      (add-to-vector-in-map m
                            (first num-and-char)
                            (second num-and-char)))
    m
    (map vector (range 1 (+ 1 (count row))) row)))

(def crates (reduce (fn [map row]
                      (add-all-in-row map row))
                    stacks
                    stack-rows))

(def instructions
  (map (fn [line]
         (as-> line val
               (str/split val #" ")
               (map (fn [str]
                      (if (every? #(Character/isDigit %) str)
                        (parse-long str)
                        (keyword str)))
                    val)))
       instructions-str))

(defn move-crate [crates from to]
  (let [crate (peek (crates from))]
    (-> crates
      (assoc from (pop (crates from)))
      (assoc to (conj (crates to) crate)))))

(defn move-crate-ntimes [crates instr]
  (nth (iterate 
         (fn [x] 
           (move-crate x (:from instr) (:to instr))) 
         crates) 
       (:move instr)))

(move-crate-ntimes crates { :move 3 :from 2 :to 1})