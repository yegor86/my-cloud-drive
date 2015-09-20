### Fix `ssh executable not found in any directories in the %PATH%`
Add  to the PATH environment variable.
* _c:\Program Files\Git\bin_
* _c:\Program Files\Git\usr\bin\_

See [Get SSH working on Vagrant/Windows/Git](https://gist.github.com/haf/2843680)

### Stop and unregister all VMs
* Go to VirtualBox installation folder: c:\Program Files\Oracle\VirtualBox\
* Run the following command
    
    <code>$ VBoxManage list runningvms </code>

    <code>$ VBoxManage controlvm <uuid> poweroff </code>
    
    <code>$ VBoxManage unregistervm <uuid> </code>