# Stop and unregister all VMs
* Go to VirtualBox installation folder: c:\Program Files\Oracle\VirtualBox\
* Run the following command
    
    <code>$ VBoxManage list runningvms </code>

    <code>$ VBoxManage controlvm <uuid> poweroff </code>
    
    <code>$ VBoxManage unregistervm <uuid> </code>