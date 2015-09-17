# In case you want to stop and unregister all VMs

## The following VirtualBox commands might help. If poweroff doesn't work, try unregistervm.
* Go to VirtualBox installation folder: c:\Program Files\Oracle\VirtualBox\
* Run the following command
<code> $ VBoxManage list runningvms </code>
<code> $ VBoxManage controlvm <uuid> poweroff </code>
<code> $ VBoxManage unregistervm <uuid> </code>