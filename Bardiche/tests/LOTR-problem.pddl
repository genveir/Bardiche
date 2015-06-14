(define (problem LOTR)
  (:domain LOTR)
  (:requirements :bardiche)
  (:objects Frodo Pippin Merry Sam Aragorn Boromir Gandalf Legolas Gimli Elrond Galadriel Saruman Sauron - character
            Fellowship Group1 Group2 Group3 - group
            Nazgul - monster
            Isengard_orcs Mordor_orcs Gondors_army Rohans_army - army
            Hobbiton Shire Bree Weathertop Rivendel Moria Northern_Rohan Southern_Rohan Helms_Deep Gondor Isengard Minas_Ithil Mordor Mount_Doom - place
            The_Ring - item)
  (:protagonist Frodo)
  (:init    
            ;; topography
            (adjacent Hobbiton Shire)
            (adjacent Shire Bree)
            (adjacent Bree Weathertop)
            (adjacent Weathertop Rivendel)
            (adjacent Rivendel Northern_Rohan)
            (adjacent Rivendel Moria)
            (adjacent Moria Southern_Rohan)
            (adjacent Northern_Rohan Southern_Rohan)
            (adjacent Southern_Rohan Isengard)
            (adjacent Southern_Rohan Helms_Deep)
            (adjacent Southern_Rohan Gondor)
            (adjacent Gondor Minas_Ithil)
            (adjacent Gondor Mordor)
            (adjacent Mordor Mount_Doom)
  
            (at Frodo Hobbiton)
            (home Frodo Hobbiton)
            (at Sam Hobbiton)
            (home Sam Hobbiton)
            (at Pippin Hobbiton)
            (home Pippin Hobbiton)
            (at Merry Hobbiton)
            (home Merry Hobbiton)
            (at Aragorn Bree)
            (home Aragorn Gondor)
            (at Gandalf Rivendel)
            (at Boromir Gondor)
            (home Boromir Gondor)
            (at Gimli Rivendel)
            (at Legolas Rivendel)
            (at Elrond Rivendel)
            (home Elrond Rivendel)
            (at Galadriel Rivendel)
            (at Saruman Isengard)
            (home Saruman Isengard)
            (at Nazgul Mordor)
            (home Nazgul Mordor)
            (at Sauron Mordor)
            (home Sauron Mordor)
            
            (intends Frodo (visited Frodo Mount_Doom))
  )
  
  (:bardichegoal
    (good
        (at Frodo Mount_Doom)
        (at Sam Mount_Doom)
    )
    (bad (not(dead Frodo)))
  )
)