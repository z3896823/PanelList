# Source this file from the project root: source tools/android-env.sh
# Existing explicit settings take precedence; defaults match Homebrew on macOS.
if [ -z "${JAVA_HOME:-}" ] && command -v brew >/dev/null 2>&1; then
    export JAVA_HOME="$(brew --prefix openjdk@17)/libexec/openjdk.jdk/Contents/Home"
fi
export ANDROID_HOME="${ANDROID_HOME:-$HOME/Library/Android/sdk}"
export PATH="$JAVA_HOME/bin:$ANDROID_HOME/cmdline-tools/22.0/bin:$ANDROID_HOME/platform-tools:$PATH"
