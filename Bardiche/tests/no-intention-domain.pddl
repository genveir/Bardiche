(define (domain no-intention)
  (:requirements :adl :domain-axioms :intentionality)
  (:types item character - object)
  (:predicates (standing ?character - character)
               (alive ?character - character)
			   (has ?character - character ?item - item))
  
;; a character wants what others have
 (:action covet
   :parameters (?character - character ?owner - character ?item - item)
   :precondition (and (has ?owner ?item)
                      (not (intends ?character (has ?character ?item))))
   :effect (intends ?character (has ?character ?item)))
   
  
;; an earthquake makes a character fall over
 (:action earthquake
   :parameters (?character - character)
   :precondition (standing ?character)
   :effect (not (standing ?character)))
   
;; a character kills a fallen character
 (:action kill
   :parameters (?killer - character ?victim - character)
   :precondition (and (not (standing ?victim))
                      (standing ?killer)
					  (alive ?victim)
					  (alive ?killer))
   :effect (not (alive ?victim))
   :agents (?killer))
   
;; a character takes an item from a corpse
 (:action pillage
   :parameters (?taker - character ?corpse - character ?item - item)
   :precondition (and (alive ?taker)
                      (not (alive ?corpse))
					  (has ?corpse ?item))
   :effect (and (not (has ?corpse ?item))
                (has ?taker ?item))
   :agents (?taker)))