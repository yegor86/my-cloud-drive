### Fix `ssh executable not found in any directories in the %PATH%`
Add  to the PATH environment variable.
* _c:\Program Files\Git\bin_
* _c:\Program Files\Git\usr\bin\_

See [Get SSH working on Vagrant/Windows/Git](https://gist.github.com/haf/2843680)

### Stop and unregister all VMs
* Go to VirtualBox installation folder: c:\Program Files\Oracle\VirtualBox\
* Run the following command
    
    <code>$ VBoxManage list runningvms</code>

    <code>$ VBoxManage controlvm <uuid> poweroff</code>
    
    <code>$ VBoxManage unregistervm <uuid></code>
    
### Delete a virtualbox machine in the GURU_MEDITATION error state
* Kill the VBoxHeadless process which holds your app port and run "vagrant destroy". You can use [TCPView](https://technet.microsoft.com/en-us/library/bb897437.aspx) in order to find pid by Local Port and/or End the process

* Destroy vm by executing

    <code>$ vagrant destroy vmname</code>