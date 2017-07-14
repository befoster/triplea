package games.strategy.persistence.memento.serializable;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.junit.Test;

import games.strategy.persistence.serializable.ProxyRegistry;
import games.strategy.util.memento.Memento;

/**
 * A fixture for testing the integration between the {@link SerializableMementoImporter} and
 * {@link SerializableMementoExporter} classes.
 */
public final class SerializableMementoImportExportIntegrationTest {
  private final ProxyRegistry proxyRegistry = ProxyRegistry.newInstance();

  @Test
  public void shouldBeAbleToRoundTripMemento() throws Exception {
    final Memento expected = newMemento();

    final byte[] bytes = exportMemento(expected);
    final Memento actual = importMemento(bytes);

    assertThat(actual, is(expected));
  }

  private static Memento newMemento() {
    return new FakeMemento("field");
  }

  private byte[] exportMemento(final Memento memento) throws Exception {
    try (final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
      final SerializableMementoExporter mementoExporter = new SerializableMementoExporter(proxyRegistry);
      mementoExporter.exportMemento(memento, baos);
      return baos.toByteArray();
    }
  }

  private static Memento importMemento(final byte[] bytes) throws Exception {
    try (final ByteArrayInputStream bais = new ByteArrayInputStream(bytes)) {
      final SerializableMementoImporter mementoImporter = new SerializableMementoImporter();
      return mementoImporter.importMemento(bais);
    }
  }
}