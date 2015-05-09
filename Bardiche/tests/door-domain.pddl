(define (domain door)
 (:requirements :adl :domain-axioms :intentionality)
 (:types item character room)
 (:constants key axe - item
             gatehouse - room)
 (:predicates (has ?character - character ?item - item)
              (at ?character - character ?room - room)
              (in ?room - room ?item - item)
              (adjacent ?room - room ?neighbor - room)
              (doorOpen))
 
 (:action move
   :parameters      (?character - character ?from - room ?to - room)
   :precondition    (and (at ?character ?from) 
                         (adjacent ?from ?to)
                         (not (= ?from ?to)))
   :effect          (and (not (at ?character ?from))
                         (at ?character ?to))
   :agents          (?character))
   
  (:action pickup
    :parameters     (?character - character ?item - item ?room - room)
    :precondition   (and (at ?character ?room)
                         (in ?room ?item))
    :effect         (and (has ?character ?item)
                         (not (in ?room ?item)))
    :agents         (?character))
    
  (:action give
    :parameters     (?giver - character ?item - item ?receiver - character ?room - room)
    :precondition   (and (not (= ?giver ?receiver))
                         (at ?giver ?room)
                         (at ?receiver ?room)
                         (has ?giver ?item))
    :effect         (and (not (has ?giver ?item))
                         (has ?receiver ?item))
    :agents         (?giver ?receiver))
    
  (:action take
    :parameters     (?taker - character ?item - item ?victim - character ?room - room)
    :precondition   (and (not (= ?taker ?victim))
                         (at ?taker ?room)
                         (at ?victim ?room)
                         (has ?victim ?item))
    :effect         (and (not (has ?victim ?item))
                         (has ?taker ?item))
    :agents         (?taker))
    
  (:action lose
    :parameters     (?character - character ?item - item ?room - room)
    :precondition   (and (at ?character ?room)
                         (has ?character ?item))
    :effect         (and (in ?room ?item)
                         (not (has ?character ?item))))
    
  (:action open
    :parameters     (?character - character)
    :precondition   (and (at ?character gatehouse)
                       (or (has ?character key)
                           (has ?character axe))
                       (not (doorOpen)))
    :effect         (doorOpen)
    :agents         (?character))
    
  (:axiom
    :vars           (?room - room ?neighbor - room)
    :context        (adjacent ?room ?neighbor)
    :implies        (adjacent ?neighbor ?room))
)