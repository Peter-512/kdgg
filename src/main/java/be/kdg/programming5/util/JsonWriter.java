package be.kdg.programming5.util;

import java.util.List;

public interface JsonWriter {
	<T> byte[] getJsonBytes(List<T> list);
}
