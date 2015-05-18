(define (domain dungeon)
  (:requirements :adl :domain-axioms :intentionality :bardiche)
  (:types item character room spell)
  (:predicates (has ?character - character ?item - item)
               (at ?character - character ?place - room)
               (at ?item - item ?place - room)
               (adjacent ?room - room ?neighbor - room)
               (memorized ?character - character ?spell - spell)
               (friends ?character - character ?friend - friend))
  
  (:action move
    :parameters     (?character - character ?from - room ?to - room)
    :precondition   (and (at ?character ?from)
                         (adjacent ?from ?to)
                         (not (= ?from ?to)))
    :effect         (and (not (at ?character ?from))
                         (at ?character ?to))
    :agents         (?character))
     
  (:action pickup
    :parameters     (?character - character ?item - item ?room - room)
    :precondition   (and (at ?character ?room)
                         (at ?item ?room))
    :effect         (and (not (at ?item ?room))
                         (has ?character ?item))
    :agents         (?character))
    