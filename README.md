<h1>🥪 Arc Deli &amp; Co. — Java CLI Ordering System</h1>
<h3><em>Capstone 2 — DELI-cious Project</em></h3>

<p>
    Arc Deli &amp; Co. is a multi-file <strong>Java command-line ordering system</strong> that lets users:
</p>
<ul>
    <li>Build custom or signature sandwiches</li>
    <li>Add drinks and chips</li>
    <li>View an order summary with running totals</li>
    <li>Generate text receipts and a CSV transaction history</li>
</ul>

<p>
    The project demonstrates: <strong>OOP</strong>, <strong>interfaces</strong>, <strong>inheritance</strong>,
    <strong>enums</strong>, <strong>file I/O</strong>, the <strong>builder pattern</strong>, and a structured
    <strong>CLI UI</strong>.
</p>

<hr>

<h2>⭐ Features</h2>

<h3>✔ Ordering Flow</h3>
<ul>
    <li>Custom sandwiches (size, bread, toppings, sauces, toasted or not).</li>
    <li>Signature sandwiches:
        <ul>
            <li>BLT</li>
            <li>Philly Cheesesteak</li>
            <li>Roast Beef Melt</li>
            <li>Veggie Deluxe</li>
        </ul>
    </li>
    <li>Add drinks (Small / Medium / Large).</li>
    <li>Add chips (any flavor name).</li>
    <li>Live order total updated as items are added.</li>
</ul>

<h3>✔ Pricing System</h3>
<ul>
    <li>Base prices by sandwich size: 4&quot;, 8&quot;, 12&quot;.</li>
    <li>Premium toppings: meats, cheeses, extra meat, extra cheese.</li>
    <li>Regular toppings, sauces, and sides are included in the base price.</li>
    <li>All pricing logic is centralized in <code>PriceList</code>.</li>
</ul>

<h3>✔ Receipts</h3>
<p>On checkout the app creates a formatted text receipt with:</p>
<ul>
    <li>Order number (timestamp-based)</li>
    <li>Date / time</li>
    <li>All items and subtotals</li>
    <li>Total</li>
    <li>Payment method (Cash or Card)</li>
</ul>

<p>Receipts are saved to:</p>
<pre><code>receipts/&lt;orderNumber&gt;.txt</code></pre>

<h3>✔ Transaction Log (CSV)</h3>
<p>Each order is also logged as a single row in:</p>
<pre><code>receipts/transactions.csv</code></pre>

<p>Columns:</p>
<pre><code>order_number,timestamp,total,payment_method,items</code></pre>

<hr>

<h2>🏗 Project Structure</h2>

<pre><code>src/
 └─ main/
     ├─ java/com/pluralsight/
     │   ├─ App.java                 // main flow controller
     │   ├─ ui/
     │   │   └─ UserInterface.java   // console UI + input helpers
     │   ├─ models/
     │   │   ├─ PricedItem.java      // interface for priced items
     │   │   ├─ Order.java           // holds a list of PricedItem
     │   │   ├─ SandwichModels.java  // Size, Bread, Topping, Sandwich + signatures
     │   │   ├─ SandwichBuilders.java// guides sandwich creation
     │   │   ├─ ExtrasBuilders.java  // guides drink &amp; chips creation
     │   │   ├─ Drink.java           // drink model
     │   │   └─ Chips.java           // chips model
     │   └─ util/
     │       ├─ PriceList.java       // all pricing logic
     │       ├─ Money.java           // money formatting helper
     │       └─ ReceiptWriter.java   // text receipts + CSV logging
     └─ receipts/
         ├─ &lt;orderNumber&gt;.txt
         └─ transactions.csv
</code></pre>

<hr>

<h2>🧠 Core Concepts</h2>

<h3>Object-Oriented Design</h3>
<ul>
    <li><strong>Encapsulation</strong> – each model owns its data and behavior (pricing, formatting, etc.).</li>
    <li><strong>Inheritance</strong> – signature sandwiches extend the base <code>Sandwich</code> class.</li>
    <li><strong>Polymorphism</strong> – <code>PricedItem</code> lets <code>Order</code> store sandwiches, drinks, and chips together.</li>
    <li><strong>Composition</strong> – <code>Order</code> is composed of multiple <code>PricedItem</code> objects.</li>
</ul>

<h3>Enums &amp; Builders</h3>
<ul>
    <li><code>Size</code>, <code>Bread</code>, and <code>ToppingType</code> enums define safe value sets.</li>
    <li><code>SandwichBuilders</code> and <code>ExtrasBuilders</code> guide the user step-by-step when building items.</li>
</ul>

<h3>File I/O</h3>
<ul>
    <li>Uses <code>Path</code>, <code>Files</code>, and <code>BufferedWriter</code>.</li>
    <li>Uses try-with-resources for safe file handling.</li>
    <li>Creates both human-readable text receipts and machine-readable CSV logs.</li>
</ul>

<h3>CLI UI &amp; Validation</h3>
<ul>
    <li><code>UserInterface</code> centralizes all printing, menus, and input.</li>
    <li>Validation loops ensure only valid menu choices and yes/no inputs are accepted.</li>
</ul>

<hr>

<h2>📊 UML Overview (Text)</h2>

<pre><code>@startuml
interface PricedItem {
  +title(): String
  +price(): BigDecimal
  +receiptLine(): String
}

class Order {
  -items: List&lt;PricedItem&gt;
  +addItem(item: PricedItem): void
  +isEmpty(): boolean
  +itemsNewestFirst(): List&lt;PricedItem&gt;
  +total(): BigDecimal
  +items(): List&lt;PricedItem&gt;
}

class Sandwich {
  -size: Size
  -bread: Bread
  -toasted: boolean
  -tops: List&lt;Topping&gt;
  +addTop(t: Topping): void
  +title(): String
  +price(): BigDecimal
  +receiptLine(): String
}

class Drink
class Chips

class BLT
class PhillyCheesesteak
class RoastBeefMelt
class VeggieDeluxe

enum Size
enum Bread
enum ToppingType

class Topping
class SandwichBuilders
class ExtrasBuilders
class PriceList
class ReceiptWriter
class Money
class UserInterface
class App

PricedItem &lt;|.. Sandwich
PricedItem &lt;|.. Drink
PricedItem &lt;|.. Chips

Sandwich &lt;|-- BLT
Sandwich &lt;|-- PhillyCheesesteak
Sandwich &lt;|-- RoastBeefMelt
Sandwich &lt;|-- VeggieDeluxe

Order "1" o-- "*" PricedItem
App ..&gt; UserInterface
App ..&gt; Order
App ..&gt; SandwichBuilders
App ..&gt; ExtrasBuilders
App ..&gt; ReceiptWriter
@enduml
</code></pre>

<hr>

<h2>▶ Running the Program</h2>

<ol>
    <li>Open the project in IntelliJ.</li>
    <li>Ensure a JDK (17 or higher) is configured.</li>
    <li>Right-click <code>App.java</code> → <strong>Run 'App'</strong>.</li>
</ol>

<p>Example start screen:</p>
<pre><code>Arc Deli &amp; Co.
• 1) New Order
• 0) Exit
Choose:</code></pre>

<hr>

<h2>🧾 Example Checkout</h2>

<pre><code>Checkout
--------------------------------------------
[8"] Toasted on wheat
- MEAT: turkey
- CHEESE: provolone
- REGULAR: lettuce
- SAUCE: mayo
  Subtotal: $9.50

Drink: Cola (MEDIUM)   $2.50
Chips: BBQ             $1.50

TOTAL: $13.50
--------------------------------------------
Payment method:
• 1) Cash
• 2) Card
Confirm checkout? (y/n):</code></pre>

<p>
    On confirmation, the app:
</p>
<ul>
    <li>Saves a text receipt in the <code>receipts</code> folder with a unique order number.</li>
    <li>Appends a record to <code>transactions.csv</code> with timestamp, total, payment method, and items.</li>
</ul>

<hr>

<h2>✔ Summary</h2>

<p>
    Arc Deli &amp; Co. is a complete, multi-file Java CLI application that simulates a deli point-of-sale system.
    It includes custom and signature sandwiches, dynamic pricing, drinks and chips, formatted text receipts,
    CSV transaction logging, and a clean, validated console UI that satisfies the Capstone 2 (DELI-cious) requirements.
</p>
