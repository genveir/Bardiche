(define (domain battle)
 (:requirements :adl :domain-axioms :intentionality)
 (:types item character place - object)
 (:predicates (has ?character - character ?item - item)
              (at ?character - character ?place - place)
              (inroom ?item - item ?place - place)
              (adjacent ?place - place ?neighbour - place)
              (alive ?character - character)
              (liege ?character - character ?liege - character)
 
 (:action move
   :parameters      (?character - character ?from - place ?to - place)
   :precondition    (and (at ?character ?from) 
                         (adjacent ?from ?to)
                         (not (= ?from ?to)))
   :effect          (and (not (at ?character ?from))
                         (at ?character ?to))
   :agents          (?character))
   
  (:action pickup
    :parameters     (?character - character ?item - item ?place - place)
    :precondition   (and (at ?character ?place)
                         (in ?item ?place))
    :effect         (and (has ?character ?item)
                         (not (in ?item ?place)))
    :agents         (?character))
    
  (:action drop
    :parameters     (?character - character ?item - item ?place - place)
    :precondition   (and (at ?character ?place)
                         (has ?character ?item))
    :effect         (and (in ?item ?place)
                         (not (has ?character ?item)))
    :agents         (?character))
    
  (:action take
    :parameters     (?taker - character ?item - item ?victim - character ?place - place)
    :precondition   (and (at ?taker ?place)
                         (at ?victim ?place)
                         (has ?victim ?item)
                         (strong ?taker)
                         (not (strong ?victim)))
    :effect         (and (not (has ?victim ?item))
                         (has ?taker ?item))
    :agents         (?taker))
   
  (:action unlock
    :parameters     (?character - character ?place - place)
    :precondition   (and (at ?character doorroom)
                         (not (doorUnlocked))
                         (not (doorBroken))
                         (has ?character key))
    :effect         (doorUnlocked)
    :agents         (?character))
    
  (:action break
    :parameters     (?character - character ?place - place)
    :precondition   (and (at ?character doorroom)
                         (not (doorBroken))
                         (has ?character axe))
    :effect         (doorBroken)
    :agents         (?character))
    
  (:action open
    :parameters     (?character - character ?place - place)
    :precondition   (and (at ?character ?place)
                       (doorPlace ?place)
                       (or (doorUnlocked) (doorBroken))
                       (not (doorOpen)))
    :effect         (doorOpen)
    :agents         (?character))
    
  (:axiom
    :vars           (?place - place ?neighbour - place)
    :context        (adjacent ?place ?neighbour)
    :implies        (adjacent ?neighbour ?place))
)