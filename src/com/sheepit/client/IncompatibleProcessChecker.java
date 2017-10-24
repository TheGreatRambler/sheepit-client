package com.sheepit.client;

import java.util.List;
import java.util.TimerTask;

import org.jutils.jprocesses.JProcesses;
import org.jutils.jprocesses.model.ProcessInfo;

import com.sheepit.client.os.OS;

public class IncompatibleProcessChecker extends TimerTask {
	
	private Client client;
	
	private boolean suspendedDueToOtherProcess;
	
	public IncompatibleProcessChecker(Client client_) {
		this.client = client_;
		this.suspendedDueToOtherProcess = false;
	}
	
	@Override
	public void run() {
		String search = this.client.getConfiguration().getIncompatibleProcessName().toLowerCase();
		this.client.getLog().debug("IncompatibleProcessChecker::run search: '" + search + "'");
		if (search == null || search.isEmpty()) { // to nothing
			return;
		}
		
		if (isSearchProcessRunning(search)) {
			if (this.client != null && this.client.getRenderingJob() != null && this.client.getRenderingJob().getProcessRender().getProcess() != null) {
				this.client.getRenderingJob().setAskForRendererKill(true);
				this.client.getRenderingJob().setUserBlockJob(true);
				OS.getOS().kill(this.client.getRenderingJob().getProcessRender().getProcess());
			}
			this.client.suspend();
			this.client.getGui().status("Client paused due to 'incompatible process' feature");
			this.suspendedDueToOtherProcess = true;
		}
		else {
			if (this.client.isSuspended() && suspendedDueToOtherProcess) {
				// restart the client since the other process has been shutdown
				this.client.resume();
			}
		}
	}

	private boolean isSearchProcessRunning(String search) {
		JProcesses processes = JProcesses.get();
		processes.fastMode();
		List<ProcessInfo> processesList = processes.listProcesses();

		for (final ProcessInfo processInfo : processesList) {
			String name = processInfo.getName().toLowerCase();
			if (name == null || name.isEmpty()) {
				continue;
			}
			
			if (name.contains(search)) {
				this.client.getLog().debug("IncompatibleProcessChecker(" + search + ") found " + processInfo.getName());
				return true;
			}
		}
		
		this.client.getLog().debug("IncompatibleProcessChecker::isSearchProcessRunning(" + search + ") not found");
		return false;
	}
}
