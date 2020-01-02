import SwiftUI
import app
import class app.NumberViewNumberIntent

struct SwiftUiNumberView: View {
    @State var state = NumberViewState(number: 0)
    
    let presenter : NumberPresenter
    let collector : NumberFlowCollector
    
    init() {
        presenter = NumberPresenter(numberInteractor: NumberInteractor())
        collector = NumberFlowCollector(numberPresenter: presenter)
    }

    var body: some View {
        HStack {
            PurpleButton(label: "-", action: {
                self.presenter.onIntent(intent: NumberViewNumberIntent.MinusIntent())
            })
            
            Text(String(state.number))
                .frame(width: 32)
            
            PurpleButton(label: "+", action: {
                self.presenter.onIntent(intent: NumberViewNumberIntent.PlusIntent())
            })
        }.onAppear {
            self.collector.start(action: { newState in self.state = newState })
        }.onDisappear {
            self.collector.stop()
        }
    }
}

struct PurpleButton: View {
    var label : String
    var action : () -> Void
    
    var body: some View {
        Button(action: action) {
            Text(label)
                .frame(width: 32)
                .padding(8)
                .background(Color.purple)
                .foregroundColor(Color.white)
                .cornerRadius(4)
        }
    }
}

struct SwiftUiNumberView_Previews: PreviewProvider {
    static var previews: some View {
        SwiftUiNumberView()
    }
}
