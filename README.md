# _CoreWar_
	
Projet d'Info4B de fin de cycle.

## _Description_

### What is Core War ?

Core War (or Core Wars) is a programming game where assembly programs try to destroy each other in the memory of a simulated computer. The programs (or warriors) are written in a special language called Redcode, and run by a program called MARS (Memory Array Redcode Simulator).

Both Redcode and the MARS environment are much simplified and abstracted compared to ordinary computer systems. This is a good thing, since CW programs are written for performance, not for clarity. If the game used an ordinary assembly language, there might be two or three people in the world capable of writing an effective and durable warrior, and even they wouldn't probably be able to understand it fully. It would certainly be challenging and full of potential, but it'd probably take years to reach even a moderate level of skill.

### How does it work ?

The system in which the programs run is quite simple. The core (the memory of the simulated computer) is a continuous array of instructions, empty except for the competing programs. The core wraps around, so that after the last instruction comes the first one again.

In fact, the programs have no way of knowing where the core ends, since there are no absolute addresses. That is, the address 0 doesn't mean the first instruction in the memory, but the instruction that contains the address. The next instruction is 1, and the previous one obviously -1.

As you can see, the basic unit of memory in Core War is one instruction, instead of one byte as is usual. Each Redcode instruction contains three parts: the OpCode itself, the source address (a.k.a. the A-field) and the destination address (the B-field). While it is possible for example to move data between the A-field and the B-field, in general you need to treat the instructions as indivisible blocks.

The execution of the programs is equally simple. The MARS executes one instruction at a time, and then proceeds to the next one in the memory, unless the instruction explicitly tells it to jump to another address. If there is more than one program running, (as is usual) the programs execute alternately, one instruction at a time. The execution of each instruction takes the same time, one cycle, whether it is `MOV`, `DIV` or even `DAT` (which kills the process).

## _RedCode_
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

## _Architecture_

* CoreWar
* MARS_CORE
    * Warrior.java
    * CPU.java
        * Instruction.java
        * Process.java
            * Argument.java
            * Register.java
* GUI

## _Etat_ 
En cours de developpement.

|MARS_CORE (terminer)   |CoreWar (en attente)   |GUI (en cours)    |
|:----------------------|:----------------------|:-----------------|
|Lucas HADEY            |Lucas Hadey            |Jonathan MARTIN   |
|CPU et dependances -> ok|                       |                  |  
|Compilateur RedCode -> ok|                       |                  |
 
#### _Groupe_ 
Jonathan MARTIN

Lucas HADEY 

--------------------------------------------------------------------------------------------------