(define (domain LOTR)
  (:requirements :adl :domain-axioms :intentionality :expression-variables :bardiche)
  (:types place gate - object
          character monster - unit
          unit group army - entity
          weapon light - item)
  (:predicates 
    
    ;; locations
    (adjacent ?place - place ?neighbor - place)
    (forest ?place - place)
    (mountains ?place - place)
    (swamp ?place - place)
    (plains ?place - place)
    (fortress ?place - place)
    (visited ?entity - entity ?place - place)
    (at ?entity - entity ?place - place)
    (home ?entity - entity)
    (protects ?gate - gate ?from - place ?to - place)
    (open ?gate - gate)
    
    ;; items
    (has ?entity - entity ?item - item)
    (has_had ?entity - entity ?item - item)
    (at ?place - place ?item - item)
    (broken ?weapon - weapon)
    (enchanting ?item - item)
    (can_be_destroyed_at ?item - item ?place - place)
    (destroyed ?item - item)
    
    
    ;; entities
    (member ?character - character ?group - group)
    (member ?monster - monster ?group - group)
    (leader ?entity - entity ?group - group)
    (attached_to ?group - group ?army - army)
    (dead ?entity - entity)
    (together ?entity - entity ?other - entity)
    (met ?entity - entity ?other - entity)
    (together_and_alive ?entity - entity ?other - entity)
    (defends ?entity - entity ?from - place ?to - place)
    (good ?entity - entity)
    (evil ?entity - entity)
    (strong ?entity - entity)
    (armed ?entity - entity)
  )
  
  (:action travel
    :parameters     (?entity - entity ?from - place ?to - place)
    :precondition   (and
                        (adjacent ?from ?to)
                        (at ?entity ?from)
                        (not (exists (?gate - gate) (protects ?gate ?from ?to)))
                        (not (exists (?entity - entity) (defends ?entity ?from ?to)))
                    )
    :effect         (and (not (at ?entity ?from))
                         (at ?entity ?to)
                         (visited ?entity ?to))
    :agents         (?entity)
    :initiator      (?entity))
    
  (:action kill
    :parameters     (?character - unit ?target - unit)
    :precondition   (and
                        (together_and_alive ?character ?target)
                        (or
                            (and (not (strong ?target))
                                 (or (strong ?character)
                                     (armed ?character)))
                            (and (strong ?target)
                                 (strong ?character)
                                 (armed ?character))))
    :effect         (dead ?target)
    :agents         (?character)
    :initiator      (?character))
    
  (:axiom
    :vars       (?from - place ?to - place)
    :context    (adjacent ?from ?to)
    :implies    (adjacent ?to ?from))
)