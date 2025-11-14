<h1>Arc Deli &amp; Co.</h1>
<h2>Capstone 2 &mdash; Java CLI Ordering System</h2>

<p>
Arc Deli &amp; Co. is a Java command-line application that allows customers to build sandwiches,
add drinks and chips, review their order, and generate receipts.
Each order is also logged into a CSV file for maintaining transaction history.
</p>

<p>
This project demonstrates OOP fundamentals, file handling, interfaces, enums, inheritance,
builder-style flows, and clean CLI UI design.
</p>

<hr>

<h1>Features</h1>

<h2>Ordering System</h2>
<ul>
  <li>Custom sandwiches (bread, size, toppings, sauces, extras)</li>
  <li>Signature sandwiches: BLT, Philly Cheesesteak, Roast Beef Melt, Veggie Deluxe</li>
  <li>Add drinks (Small / Medium / Large)</li>
  <li>Add chips (any flavor)</li>
  <li>Toasted or untoasted</li>
  <li>Real-time running total</li>
  <li>Accurate size-based pricing using a centralized price list</li>
</ul>

<h2>Receipt Generation</h2>
<p>A formatted receipt is created for each order:</p>
<pre><code>/receipts/&lt;orderNumber&gt;.txt
</code></pre>

<p>Each receipt includes:</p>
<ul>
  <li>Order number</li>
  <li>Timestamp</li>
  <li>Payment method (Cash / Card)</li>
  <li>Full item breakdown</li>
  <li>Subtotal and total</li>
</ul>

<h2>Transaction Log (CSV)</h2>
<p>Each order is appended to a CSV file:</p>
<pre><code>/receipts/transactions.csv
</code></pre>

<p>CSV columns:</p>
<pre><code>order_number,timestamp,total,payment_method,items
</code></pre>

<hr>

<h1>Project Structure</h1>

<pre><code>src/
 └─ main/
     ├─ java/
     │   └─ com/pluralsight/
     │       ├─ App.java
     │       ├─ ui/
     │       │   └─ UserInterface.java
     │       ├─ models/
     │       │   ├─ Order.java
     │       │   ├─ PricedItem.java
     │       │   ├─ SandwichModels.java
     │       │   ├─ SandwichBuilders.java
     │       │   ├─ ExtrasBuilders.java
     │       │   ├─ Drink.java
     │       │   └─ Chips.java
     │       └─ util/
     │           ├─ PriceList.java
     │           ├─ Money.java
     │           └─ ReceiptWriter.java
     └─ resources/
         └─ receipts/
             ├─ &lt;orderNumber&gt;.txt
             └─ transactions.csv
</code></pre>

<hr>

<h1>Setup</h1>

<h2>Requirements</h2>
<ul>
  <li>Java 17 or higher</li>
  <li>IntelliJ IDEA (recommended)</li>
</ul>

<h2>Running the App</h2>
<ol>
  <li>Open the project in IntelliJ.</li>
  <li>Locate <code>App.java</code>.</li>
  <li>Right-click <code>App.java</code> &rarr; <strong>Run 'App'</strong>.</li>
</ol>

<p>
Make sure the IntelliJ <strong>Working Directory</strong> is set to the project root so
receipts and the CSV file are created in the correct <code>/receipts</code> folder.
</p>

<hr>

<h1>Core Concepts Demonstrated</h1>

<h2>Object-Oriented Programming</h2>
<ul>
  <li><strong>Encapsulation</strong>: models manage their own data and pricing logic.</li>
  <li><strong>Inheritance</strong>: signature sandwiches extend the base <code>Sandwich</code> class.</li>
  <li><strong>Polymorphism</strong>: the <code>PricedItem</code> interface allows the order to store any priced item (sandwich, drink, chips).</li>
  <li><strong>Enums</strong>: types for sizes, bread, and topping categories.</li>
  <li><strong>Builder-style flows</strong>: <code>SandwichBuilders</code> and <code>ExtrasBuilders</code> guide the user through creating items.</li>
</ul>

<h2>File I/O</h2>
<ul>
  <li>Text receipts for each order.</li>
  <li>CSV logging of all transactions.</li>
  <li>Uses <code>BufferedWriter</code> and try-with-resources for safe file writing.</li>
</ul>

<h2>Clean UI and Validation</h2>
<ul>
  <li>Centralized <code>UserInterface</code> class for all console prompts and menus.</li>
  <li>Input validation with re-prompts on invalid entries.</li>
</ul>

<hr>

<h1>Ordering Flow</h1>

<ol>
  <li>User starts a new order from the home menu.</li>
  <li>Selects a sandwich:
    <ul>
      <li>Signature sandwich (BLT, Philly, Roast Beef Melt, Veggie Deluxe), or</li>
      <li>Custom sandwich (choose size, bread, toppings, sauces, extras).</li>
    </ul>
  </li>
  <li>Adds optional items:
    <ul>
      <li>Drinks (small, medium, large)</li>
      <li>Chips (any flavor)</li>
    </ul>
  </li>
  <li>Reviews the order and sees the running total.</li>
  <li>Chooses payment method (Cash or Card).</li>
  <li>Confirms checkout:
    <ul>
      <li>Receipt is written to <code>/receipts/&lt;orderNumber&gt;.txt</code>.</li>
      <li>Transaction is logged to <code>/receipts/transactions.csv</code>.</li>
    </ul>
  </li>
</ol>

<hr>

<h1>Sample Receipt</h1>

<pre><code>Arc Deli &amp; Co.
Order #: 20251113-194230
Date:    2025-11-13 19:42:30
Payment: Cash
------------------------------------
8" Toasted on white
 - MEAT: bacon
 - CHEESE: cheddar
 - REGULAR: lettuce
Subtotal: $10.50

Medium Cola      $2.50
BBQ Chips        $1.50
------------------------------------
TOTAL: $15.00
Thank you!
</code></pre>

<hr>

<h1>Summary</h1>

<p>
Arc Deli &amp; Co. is a complete CLI-based deli ordering system built in Java.
It provides dynamic sandwich building, signature options, drinks and chips,
accurate pricing, text receipt generation, and CSV transaction logging.
The design uses clean OOP structure and is aligned with the requirements for Capstone 2.
</p>
