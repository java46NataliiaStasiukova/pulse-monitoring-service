package telran.monitoring.service;

import telran.monitoring.model.PulseJump;
import telran.monitoring.model.PulseProbe;

public interface AnalyzerService {

	PulseJump processPulseProbe(PulseProbe probe);
}
