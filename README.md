# aka-supertmarket

# kata-supermarket-pricing
The problem domain is something seemingly simple: pricing goods at supermarkets.
Some things in supermarkets have simple prices: this can of beans costs $0.65. Other things have more complex prices. For example:
• three for a dollar (so what’s the price if I buy 4, or 5?)
• $1.99/pound (so what does 4 ounces cost?)
• buy two, get one free (so does the third item have a price?)
The exercise is to experiment with a model that is flexible enough to deal with these (and other) pricing schemes, and at the same time are generally usable how do you keep an audit trail of pricing decisions.

# Implementation choices
In this supermarket, you can buy whatever you wish. there is no stock management, as it was not specified in the subject.
Basically, A customer has got a cart, and is free to buy whatever items he wants, which are then added to the cart. When the customer doesn't need anything else, he goes to the supermarket checkout, which is in charge of calculating the bill incrementally, according to each item found in the cart. The customer's cart is in charge of making sure that the customer doesn't try anything suspicious (i.e. adding a decimal value of an item that is bought in unitary value). I assumed that the supermarket itself would not have such items and then decided to control this directly in the cart addition method.

In terms of structure, I decided to implement a sort of decorator pattern, which decides of the pricing calculation method that is to be applied to an item, according to its properties. Additionnally, the features "Three for a dollar" and "Buy two, get one free" are implemented in a more generic way to the sole "PackagePricing" class. That can be explained by the fact I assumed there was always a unitary price for which you could acquire one unit of desired item. It means that "three for a dollar", is then a specific price applied for getting three units specifically, while the "buy two, get one free", is also a discount of 33% if you get three units. However, getting one unit of the aforementioned item will still result in the price being higher since the reduction won't be applied. Along with the PackagePricing method, there is a DefaultPricing method that includes the "$1.99/pound" calculation. The DefaultPricing method will calculate the price as a flat multiplication of a float and a price, as the customer's cart is in charge of enabling the pricing by weight according to the item selected.

------------------------------------------------------------------------------------------------
# Installation
For local development:

mvn clean install

# Tests
For unit tests:

mvn test
Note I: In Eclipse you can run all the tests by right-clicking on the project and selecting Run As->JUnit Test.

------------------------------------------------------------------------------------------------

# Usage
open a terminal

cd to aka-supermarket project direcotry

mvn clean install

# files
In the file /aka-supermarket/src/main/resources/application-default.yml you find the path to install the différents files

Note I: example cart.json location: /aka-supermarket/src/main/resources/basket.json

Note II: example discounts.json location: /aka-supermarket/src/main/resources/discounts.json

Note II: example items.json location: /aka-supermarket/src/main/resources/items.json

Note IV: example cart.test.json location: /aka-supermarket/src/main/resources/basket.test.json

Note V: example discounts.test.json location: /aka-supermarket/src/main/resources/discounts.test.json

Note VI: example items.test.json location: /aka-supermarket/src/main/resources/items.test.json

Note VII: example of java command with file paths : java -jar -target/aka-supermarket-local.jar
