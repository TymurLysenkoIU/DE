# Installing development dependencies

```shell script
npm install
```

# Building

Start `sbt shell`:

```shell script
sbt
```

- Inside `sbt shell`:

  For fast optimization:
  ```sbtshell
  compile
  fastOptJS
  ```

  And then make sure the `./src/main/resources/index.html` references the fast optimized js file:
  ```html
  <script type="text/javascript" src="../../../target/scala-2.12/de-assignment-fastopt.js"></script>
  ```

- For the full optimization change the last command inside the `sbt shell` to:
  ```sbtshell
  fullOptJS
  ```

  And then make sure the `./src/main/resources/index.html` references the fast optimized js file:
  ```html
  <script type="text/javascript" src="../../../target/scala-2.12/de-assignment-opt.js"></script>
  ```

# Runing

Open `index.html` in a browser.
