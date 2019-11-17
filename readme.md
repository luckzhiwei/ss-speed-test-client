### Shadowsocks vpn speed-test android client

* [intro](.github/aboud.md)   
* [help](.github/faq.md)
* build:
    * git clone 
    * cd ss-speed-test 
    * git submodule update --init --recursive (fetch the source code for shaowsocks)
    * ./gradlew assDebug
    * note: 
            
          1.  You should ddd your NDK-Toolchains to your path to use ndk-build cmd.
          2. I suppose you use NDK-16 for the project toolchains to avoid the question that(https://stackoverflow.com/questions/35128229/error-no-toolchains-found-in-the-ndk-toolchains-folder-for-abi-with-prefix-llv)
    

  
