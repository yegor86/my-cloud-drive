### Fix `ssh executable not found in any directories in the %PATH%`
Add  to the PATH environment variable.
* _c:\Program Files\Git\bin_
* _c:\Program Files\Git\usr\bin\_

See [Get SSH working on Vagrant/Windows/Git](https://gist.github.com/haf/2843680)

### Fix `Your VM has become "inaccessible." Unfortunately, this is a critical error`
* Go to VirtualBox installation folder: c:\Program Files\Oracle\VirtualBox\
* Run the following command
    
    <code>$ VBoxManage list vms</code>

    <code>$ VBoxManage unregistervm <uuid></code>
    
### Stop and unregister VM or VM that is in GURU_MEDITATION or any other locked state
* Go to VirtualBox installation folder: c:\Program Files\Oracle\VirtualBox\
* Run the following command
    
    <code>$ VBoxManage list runningvms</code>

    <code>$ VBoxManage controlvm <uuid> poweroff</code>
    
    <code>$ VBoxManage unregistervm <uuid></code>
    
### Stop a virtualbox machine in the GURU_MEDITATION error state
* Get VM list

    <code>$ VBoxManage list vms</code>    
* Power off VM
    
    <code>$ VBoxManage controlvm <uuid> poweroff</code>