<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Arc Deli &amp; Co. — Java CLI Ordering System</title>
    <style>
        body {
            font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", sans-serif;
            line-height: 1.5;
            max-width: 900px;
            margin: 0 auto;
            padding: 2rem;
            background: #0f172a;
            color: #e5e7eb;
        }
        h1, h2, h3, h4 {
            color: #fbbf24;
        }
        code {
            font-family: "JetBrains Mono", Consolas, monospace;
            background: #020617;
            padding: 2px 4px;
            border-radius: 4px;
        }
        pre {
            background: #020617;
            padding: 1rem;
            border-radius: 8px;
            overflow-x: auto;
            border: 1px solid #1f2937;
        }
        .tag {
            display: inline-block;
            padding: 2px 8px;
            border-radius: 999px;
            background: #1f2937;
            font-size: 0.8rem;
            margin-right: 4px;
        }
        table {
            border-collapse: collapse;
            width: 100%;
            margin: 1rem 0;
            background: #020617;
        }
        th, td {
            border: 1px solid #1f2937;
            padding: 0.5rem 0.75rem;
            text-align: left;
        }
        th {
            background: #111827;
        }
        a {
            color: #60a5fa;
        }
        hr {
            border: none;
            border-top: 1px solid #1f2937;
            margin: 2rem 0;
        }
    </style>
</head>
<body>

<h1>🥪 Arc Deli &amp; Co. — Java CLI Ordering System</h1>
<h3><em>Capstone 2 — DELI-cious Project</em></h3>

<p>
    Arc Deli &amp; Co. is a multi-file <strong>Java command-line ordering system</strong> that allows users to:
</p>
<ul>
    <li>Build custom or signature sandwiches</li>
    <li>Add drinks and chips</li>
    <li>View an order summary with running totals</li>
    <li>Generate text receipts and a CSV transaction history</li>
</ul>

<p>
    The project demonstrates:
</p>
<p>
    <span class="tag">OOP</span>
    <span class="tag">Interfaces</span>
    <span class="tag">Inheritance</span>
    <span class="tag">Enums</span>
    <span class="tag">File I/O</span>
    <span class="tag">Builder Pattern</span>
    <span class="tag">CLI UI</span>
</p>

<hr>

<h2>⭐ Features</h2>

<h3>✔ Full Ordering Flow</h3>
<ul>
    <li>Create <strong>custom sandwiches</strong> (size, bread, toppings, sauces, toasted).</li>
    <li>Choose from <strong>signature sandwiches</strong> (implemented via inheritance):
        <ul>
            <li>BLT</li>
            <li>Philly Cheesesteak</li>
            <li>Roast Beef Melt</li>
            <li>Veggie Deluxe</li>
        </ul>
    </li>
    <li>Add <strong>drinks</strong> (Small, Medium, Large).</li>
    <li>Add <strong>chips</strong> (any flavor).</li>
    <li>See a <strong>live order total</strong> as items are added.</li>
</ul>

<h3>✔ Pricing System</h3>
<ul>
    <li>Base prices by sandwich size: 4&quot;, 8&quot;, 12&quot;.</li>
    <li>Premium toppings: meats, cheeses, extra meat, extra cheese.</li>
    <li>Regular toppings, sauces, and sides are included in the price.</li>
    <li>All prices are centralized in a <code>PriceList</code> utility class.</li>
</ul>

<h3>✔ Receipts (Text Files)</h3>
<p>
    On checkout, a formatted <code>.txt</code> receipt is created with:
</p>
<ul>
    <li>Order number (timestamp-based)</li>
    <li>Date and time</li>
    <li>Items and line details</li>
    <li>Order total</li>
    <li>Payment method (Cash or Card)</li>
</ul>

<p>Receipts are saved to:</p>
<pre><code>receipts/&lt;orderNumber&gt;.txt</code></pre>

<h3>✔ Transaction Log (CSV)</h3>
<p>
    Every order also logs one row into a persistent transaction history:
</p>
<pre><code>receipts/transactions.csv</code></pre>

<p>Columns:</p>
<pre><code>order_number,timestamp,total,payment_method,items</code></pre>

<p>
    This CSV allows reviewing all past customer orders, totals, and payment methods in one place.
</p>

<hr>

<h2>🏗 Project Structure</h2>

<pre><code>src/
 └─ main/
     ├─ java/com/pluralsight/
     │   ├─ App.java                 // main controller / flow
     │   ├─ ui/
     │   │   └─ UserInterface.java   // console UI helpers
     │   ├─ models/
     │   │   ├─ PricedItem.java      // interface for priced items
     │   │   ├─ Order.java           // holds a list of PricedItem
     │   │   ├─ SandwichModels.java  // Size, Bread, Topping, Sandwich + signatures
     │   │   ├─ SandwichBuilders.java// guides sandwich creation
     │   │   ├─ ExtrasBuilders.java  // guides drink & chips creation
     │   │   ├─ Drink.java           // drink model
     │   │   └─ Chips.java           // chips model
     │   └─ util/
     │       ├─ PriceList.java       // all pricing data
     │       ├─ Money.java           // formatting helper for money
     │       └─ ReceiptWriter.java   // text & CSV receipts
     └─ resources/
         └─ (optional, used by some configs)</code></pre>

<hr>

<h2>🧠 Core Concepts Demonstrated</h2>

<h3>Object-Oriented Design</h3>
<ul>
    <li><strong>Encapsulation</strong> — Each model class owns its own data and behavior (pricing, formatting, etc.).</li>
    <li><strong>Inheritance</strong> — Signature sandwiches extend a base <code>Sandwich</code> class.</li>
    <li><strong>Polymorphism</strong> — <code>PricedItem</code> interface lets <code>Order</code> store sandwiches, drinks, and chips together.</li>
    <li><strong>Composition</strong> — <code>Order</code> is composed of multiple <code>PricedItem</code> instances.</li>
</ul>

<h3>Enums and Types</h3>
<ul>
    <li><code>Size</code>, <code>Bread</code>, and <code>ToppingType</code> enums define safe, readable value sets.</li>
</ul>

<h3>Builder Pattern</h3>
<ul>
    <li><code>SandwichBuilders</code> and <code>ExtrasBuilders</code> encapsulate step-by-step creation logic to keep <code>App</code> clean.</li>
</ul>

<h3>File I/O</h3>
<ul>
    <li>Uses <code>Path</code>, <code>Files</code>, and <code>BufferedWriter</code>.</li>
    <li>Applies <strong>try-with-resources</strong> to safely handle file streams.</li>
    <li>Outputs both human-readable text receipts and machine-readable CSV logs.</li>
</ul>

<h3>CLI UI and Validation</h3>
<ul>
    <li><code>UserInterface</code> centralizes console printing and input handling.</li>
    <li>Validation loops ensure that menu choices and yes/no inputs are valid.</li>
</ul>

<hr>

<h2>📊 UML Overview</h2>

<p>This is a high-level UML-style view of the main classes and relationships.</p>

<h3>Class Diagram (Text / PlantUML Style)</h3>

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

class Drink {
  -size: DSize
  -flavor: String
  +title(): String
  +price(): BigDecimal
  +receiptLine(): String
}

class Chips {
  -type: String
  +title(): String
  +price(): BigDecimal
  +receiptLine(): String
}

class BLT
class PhillyCheesesteak
class RoastBeefMelt
class VeggieDeluxe

enum Size
enum Bread
enum ToppingType

class Topping {
  -name: String
  -type: ToppingType
  -qty: int
  +makeExtra(): void
}

class SandwichBuilders {
  +build(ui: UserInterface): PricedItem
}

class ExtrasBuilders {
  +buildDrink(ui: UserInterface): PricedItem
  +buildChips(ui: UserInterface): PricedItem
}

class PriceList {
  +bread(sizeKey: String): BigDecimal
  +meat(sizeKey: String): BigDecimal
  +extraMeat(sizeKey: String): BigDecimal
  +cheese(sizeKey: String): BigDecimal
  +extraCheese(sizeKey: String): BigDecimal
  +drink(sizeName: String): BigDecimal
  +chips(): BigDecimal
}

class ReceiptWriter {
  +saveReceipt(order: Order, paymentMethod: String): String
}

class Money {
  +format(v: BigDecimal): String
}

class UserInterface {
  +header(text: String): void
  +sub(text: String): void
  +line(): void
  +option(text: String): void
  +info(text: String): void
  +warn(text: String): void
  +ok(text: String): void
  +ask(prompt: String): String
  +pick(prompt: String): int
  +pickInRange(prompt: String, min: int, max: int): int
  +askYesNo(prompt: String): boolean
  +pause(): void
}

class App {
  -ui: UserInterface
  +run(): void
}

PricedItem &lt;|.. Sandwich
PricedItem &lt;|.. Drink
PricedItem &lt;|.. Chips

Sandwich &lt;|-- BLT
Sandwich &lt;|-- PhillyCheesesteak
Sandwich &lt;|-- RoastBeefMelt
Sandwich &lt;|-- VeggieDeluxe

Order "1" o-- "*" PricedItem
SandwichBuilders ..&gt; UserInterface
ExtrasBuilders ..&gt; UserInterface
SandwichBuilders ..&gt; Sandwich
ExtrasBuilders ..&gt; Drink
ExtrasBuilders ..&gt; Chips

ReceiptWriter ..&gt; Order
ReceiptWriter ..&gt; PricedItem

App ..&gt; UserInterface
App ..&gt; Order
App ..&gt; SandwichBuilders
App ..&gt; ExtrasBuilders
App ..&gt; ReceiptWriter
App ..&gt; Money
@enduml</code></pre>

<p>
    This UML diagram shows:
</p>
<ul>
    <li><strong>Order</strong> aggregates multiple <code>PricedItem</code> objects.</li>
    <li><strong>Sandwich</strong>, <strong>Drink</strong>, and <strong>Chips</strong> all implement <code>PricedItem</code>.</li>
    <li>Signature sandwiches inherit from <code>Sandwich</code>.</li>
    <li>Builders depend on models and <code>UserInterface</code>.</li>
    <li><code>ReceiptWriter</code> depends on <code>Order</code> and <code>PricedItem</code> to build receipts.</li>
    <li><code>App</code> is the controller that ties everything together.</li>
</ul>

<h3>Class Responsibility Summary (CRC-style)</h3>

<table>
    <thead>
    <tr>
        <th>Class</th>
        <th>Responsibility</th>
        <th>Collaborators</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>App</td>
        <td>Main flow controller: home screen, order menu, checkout, payment.</td>
        <td>UserInterface, Order, SandwichBuilders, ExtrasBuilders, ReceiptWriter, Money</td>
    </tr>
    <tr>
        <td>UserInterface</td>
        <td>All console input/output: headers, menus, prompts, validation.</td>
        <td>Used by App and builder classes.</td>
    </tr>
    <tr>
        <td>Order</td>
        <td>Holds a list of <code>PricedItem</code>, calculates total, supports display order.</td>
        <td>PricedItem, Sandwich, Drink, Chips</td>
    </tr>
    <tr>
        <td>PricedItem</td>
        <td>Interface for anything with a title, price, and receipt line.</td>
        <td>Sandwich, Drink, Chips</td>
    </tr>
    <tr>
        <td>Sandwich</td>
        <td>Represents a customized sandwich (size, bread, toasted, toppings, pricing).</td>
        <td>Topping, ToppingType, PriceList</td>
    </tr>
    <tr>
        <td>BLT / Philly / etc.</td>
        <td>Preconfigured signature sandwiches with fixed toppings.</td>
        <td>Sandwich</td>
    </tr>
    <tr>
        <td>Drink</td>
        <td>Represents a drink with size and flavor; handles its own pricing.</td>
        <td>PriceList</td>
    </tr>
    <tr>
        <td>Chips</td>
        <td>Represents a chips choice with flavor; handles its own pricing.</td>
        <td>PriceList</td>
    </tr>
    <tr>
        <td>SandwichBuilders</td>
        <td>Guides the user through building custom or signature sandwiches.</td>
        <td>UserInterface, Sandwich, BLT, PhillyCheesesteak, RoastBeefMelt, VeggieDeluxe</td>
    </tr>
    <tr>
        <td>ExtrasBuilders</td>
        <td>Guides the user through building drinks and chips.</td>
        <td>UserInterface, Drink, Chips</td>
    </tr>
    <tr>
        <td>PriceList</td>
        <td>Central place for all pricing rules and constants.</td>
        <td>Sandwich, Drink, Chips</td>
    </tr>
    <tr>
        <td>Money</td>
        <td>Formats <code>BigDecimal</code> values as monetary strings (e.g., 0.00).</td>
        <td>Used in App, receipts, and output.</td>
    </tr>
    <tr>
        <td>ReceiptWriter</td>
        <td>Saves text receipts and appends to CSV transaction log with payment method.</td>
        <td>Order, PricedItem, Money</td>
    </tr>
    </tbody>
</table>

<hr>

<h2>▶ Running the Program</h2>

<ol>
    <li>Open the project in IntelliJ.</li>
    <li>Ensure a JDK (17 or above) is configured.</li>
    <li>Right-click <code>App.java</code> &rarr; <strong>Run 'App'</strong>.</li>
</ol>

<p>Example start-up screen:</p>

<pre><code>Arc Deli &amp; Co.
• 1) New Order
• 0) Exit
Choose:</code></pre>

<hr>

<h2>🧾 Example Checkout Output</h2>

<pre><code>Checkout
────────────────────────────────────────────
[8"] Toasted on wheat
   - MEAT: turkey
   - CHEESE: provolone
   - REGULAR: lettuce
   - SAUCE: mayo
Subtotal: $9.50

Drink: Cola (MEDIUM)   $2.50
Chips: BBQ             $1.50

TOTAL: $13.50
────────────────────────────────────────────
Payment method:
• 1) Cash
• 2) Card
Confirm checkout? (y/n):</code></pre>

<p>
    When confirmed, the program:
</p>
<ul>
    <li>Creates a receipt file in the <code>receipts</code> folder with a unique order number.</li>
    <li>Appends a row to <code>transactions.csv</code> with timestamp, total, payment method, and items.</li>
</ul>

<hr>

<h2>✔ Summary</h2>

<p>
    Arc Deli &amp; Co. is a complete, multi-file, package-structured Java CLI application that simulates a deli
    point-of-sale system. It includes:
</p>
<ul>
    <li>Custom and signature sandwich building</li>
    <li>Dynamic pricing based on size and toppings</li>
    <li>Drinks and chips as separate models</li>
    <li>Formatted text receipts and CSV transaction logging</li>
    <li>Clear CLI menus and robust input handling</li>
</ul>

<p>
    The design follows good OOP practices, leverages interfaces and inheritance, and cleanly separates UI,
    business logic, and persistence concerns—meeting and exceeding the Capstone 2 (DELI-cious) requirements.
</p>

</body>
</html>
