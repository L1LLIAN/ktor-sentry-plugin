# ktor-sentry-plugin
[example usage](https://github.com/L1LLIAN/ktor-sentry-plugin/blob/mistress/src/test/kotlin/lol/lily/ktor/sentry/PluginTest.kt#L12)

Automatically capture uncaught exceptions with Sentry

repository:
```
kotlin gradle:
maven("https://maven.saph.llc/releases")

groovy gradle:
maven {
    url "https://maven.saph.llc/releases"
}

maven:
<repository>
  <id>sapphiretech-repo-releases</id>
  <name>Sapphire Technologies Repository</name>
  <url>https://maven.saph.llc/releases</url>
</repository>
```

dependency:
```
kotlin gradle:
implementation("lol.lily:ktor-sentry-plugin:1.0")

groovy gradle:
implementation "lol.lily:ktor-sentry-plugin:1.0"

maven:
<dependency>
  <groupId>lol.lily</groupId>
  <artifactId>ktor-sentry-plugin</artifactId>
  <version>1.0</version>
</dependency>
```