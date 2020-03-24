# _CoreWar_
	
Projet d'Info4B de fin de cycle.

## __Description__



## __RedCode__
Support RedCode '94 Standard and Labels.

### Instruction set

Available instructions :

|Instruction Type       |Mnemomic               |Explenations      |
|:----------------------|:----------------------|:-----------------|
|Move                   |MOV            |copies data from one address to another   |
|Add                    |ADD                       |adds one number to another     |  
|Substract              |SUB                       |subtracts one number from another|
|Jump |JMP | continues execution from another address |
| Jump if zero | JMZ | tests a number and jumps to an address if it's 0 |
| Jump if not zero | JMN | tests a number and jumps if it isn't 0 |
| Decrement and jump if not zero | DJN | decrements a number by one, and jumps unless the result isn't 0 |
| Decrement and jump if zero | DJZ | decrements a number by one, and jumps unless the result is 0 |
| Compare | CMP | compares two instructions, and skips the next instruction if they aren't equal |
|Data statement | DAT | kills the process |


Available addressing modes :

|Mnemomic   |addressing mode    |
|:----------|:------------------|
| #         | immediate         |
|           | direct            |
| @         | B-field indirect  |

### Exemple 

Dwarf.asm :
    
```assembly
;the dwarf
loop ADD #4, bomb
     MOV bomb, @bomb
     JMP loop
bomb DAT #0, #0     
```

### __Architecture__

* CoreWar
* MARS_CORE
    * Program.java
    * CPU.java
        * Instruction.java
        * Process.java
            * Argument.java
            * Register.java
* GUI

### __Etat__ 
En cours de developpement.

|MARS_CORE (terminer)   |CoreWar (en attente)   |GUI (en cours)    |
|:----------------------|:----------------------|:-----------------|
|Lucas HADEY            |Lucas Hadey            |Jonathan MARTIN   |
|CPU et dependances -> ok|                       |                  |  
|Compilateur RedCode -> ok|                       |                  |
 
#### __Groupe__ 
Jonathan MARTIN
Lucas HADEY 

--------------------------------------------------------------------------------------------------